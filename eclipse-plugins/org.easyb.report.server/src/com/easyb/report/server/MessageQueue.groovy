package com.easyb.report.server;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Provides a wrapper around a queue of messages
 */
public class MessageQueue {

	def linkedBlockingQueue = new LinkedBlockingQueue<Message>()

	def addMessage(message){
		linkedBlockingQueue.put(message)
	}

	/**TODO could change to
	 * list.add(linkedBlockingQueue.take())
	 * linkedBlockingQueue.drainTo(list)
	 * To block until 1 or more are available then 
	 * read the lot. Or just linkedBlockingQueue.drainTo(list)
	 * but I don`t think that blocks 
	**/
	def getNextMessage(){
		linkedBlockingQueue.take()
	}
}
