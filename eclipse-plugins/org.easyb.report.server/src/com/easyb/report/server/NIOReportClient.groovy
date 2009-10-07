package com.easyb.report.server;

import java.nio.channels.SocketChannel;

import com.sun.corba.se.impl.ior.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;

public class NIOReportClient {

	def host
	def port
	private def selector
	private def pendingChanges
	private ByteBuffer buffer = ByteBuffer.allocate(4096)
	def ReportClient(){
		init()
	}

	def connect(){
		def socketChannel = SocketChannel.open()
		socketChannel.configureBlocking(false)
		socketChannel.connect(new InetSocketAddress(this.host,this.port))

		// Queue a channel registration since the caller is not the 
		// selecting thread. As part of the registration we'll register
	    // an interest in connection events. These are raised when a channel
	    // is ready to complete connection establishment.
	    //synchronized(this.pendingChanges) {
	     // this.pendingChanges.add(new ChangeRequest(socketChannel, ChangeRequest.REGISTER, SelectionKey.OP_CONNECT));
	    //}
		
		return socketChannel
	}
	
	def sendReportMessage(behaviourId,message){

		buffer.cl
		def socket = connect()

	}

	private def init(){
		selector = SelectorProvider.provider().openSelector()
	}
	
	private def sendMessages(){
		
	}
}
