package com.easyb.report.server;

import java.net.InetAddress;
import java.io.BufferedOutputStream;
import java.net.Socket;

public class ReportClient {

	def host
	def port 
	def timeout = 60000
	
	Socket socket
	
	def connect(){

		if(socket){
			socket = null
		}
		
		def sockAddr = new InetSocketAddress(host,port)
		socket = new Socket()
		socket.connect(sockAddr,timeout)
	}

	def writeMessage(type,behaviourId,message){
		connect()
		def out = null
		try{
			
			out = socket.getOutputStream()
			out.write(type.value())
			
			if(!behaviourId || behaviourId.trim().length()==0){
				throw new MessageException("Message is invalid as behaviour id is not set, msg:$message type:$type")
			}

			out.write(behaviourId.length())
			
			if(message){
				out.write(message.length())	
			}
			
			out.write(behaviourId.getBytes(),0,behaviourId.length())
			out.write(message.getBytes(),0,message.length()) 
			
		}finally
		{
			if(out){
				out.flush()
				out.close()
			}
			
			if(socket && socket.isConnected()){
				socket.close()
			}
		}
	}

	def writeReport(behaviourId,report){
		
	}

}
