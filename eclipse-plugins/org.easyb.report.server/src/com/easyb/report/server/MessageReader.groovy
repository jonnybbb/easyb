package com.easyb.report.server;

public class MessageReader {
	//Map of running behaviours based on file name 
	//and associated closures used to read the data
	//the behaviourReportMap is for reports 
	//and the behaviourMessageMap is for all other 
	//messages like exceptions
	def behaviourReportMap = [:]
    def behaviourMessageMap = [:]
	def messageQueue
	def stop = false

	def execute(){
		while(!isStopping()){
			readNextMessage()
		}
	}

	def synchronized stop(){
		stop = true
	}

	def synchronized isStopping(){
		return stop
	}
	
	def readNextMessage(){
		def message = messageQueue.getNextMessage()
		def reader = getReaderClosureforMessage(message)
		
		if(!reader){
			throw new MessageException("Unknown end point for message ${message.behaviourName}")
		}

		reader(message)
	}

	def getReaderClosureforMessage(message){

		switch(message.type)
		{
		case MessageTypeEnum.REPORT: 
				return behaviourReportMap[message.behaviourName] 
			
		case MessageTypeEnum.MESSAGE:
				return behaviourMessageMap[message.behaviourName] 
		default:
			throw new MessageException("Unknown message type ${message.type} for message {$message}")
		}
		
	}
}
