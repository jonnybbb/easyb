import com.easyb.report.server.ReportClient;
import com.easyb.report.server.MessageTypeEnum;
import com.easyb.report.server.ReportServer;


scenario "Report server starts and waits for a message",{
	given "Report server is running",{

		def reportRun = false
		def messageRun = false
		def reportClos = {msg-> 
				println(msg?.behaviourName +":" + msg?.message+":"+msg?.type)
				reportRun = true}
		
		def msgClos = {msg-> 
			println(msg?.behaviourName +":" + msg?.message+":"+msg?.type)
			messageRun = true}
			
		def behaviourReportMap = ["testid":reportClos]
        def behaviourMessageMap = ["testid":msgClos]
        
		def server =  
			new ReportServer(3495,behaviourReportMap,behaviourMessageMap)

		Thread.start{server.execute()}
	}
	
	when "A message is sent from a client",{
		ReportClient client = new ReportClient(host:'localhost',port:3495)
		client.writeMessage(MessageTypeEnum.MESSAGE,"testid","Test message")
	}
	
	then "The message should be written to a reader eventually",{
		//TODO check message has been written
		while(!reportRun && !messageRun){}
	}
}

after "stop report server" , {
	 then "report server should be shut down",{
		 server.stop()
	 }
}
