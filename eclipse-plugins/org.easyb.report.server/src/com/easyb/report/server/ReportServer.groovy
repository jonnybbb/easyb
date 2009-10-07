package com.easyb.report.server;

import java.nio.channels.Selector;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;

/**
 * Simple socket server to read messages sent from easyb.
 * It should be quick to start/shutdown and have small message
 * overhead
 */
public class ReportServer{

	def selectorTimeout = 1000 //1 second by default 
	def port
	def behaviourReportMap
	def behaviourMessageMap
	
	private def mainChannel
	private def selector
	private def msgBuilder
	private def msgReader
	private def stop
	public ReportServer(port,behaviourReportMap,behaviourMessageMap){
		this.port = port
		this.behaviourReportMap = behaviourReportMap
		this.behaviourMessageMap = behaviourMessageMap
		
		init()
	}
	
	private def init(){

		//Set up the message reader/writer
		def queue = new MessageQueue()
		msgBuilder = new MessageBuilder(messageQueue:queue)

		msgReader = 
			new MessageReader(behaviourReportMap:behaviourReportMap,
				behaviourMessageMap:behaviourMessageMap,messageQueue:queue)

		//Start the reader
		Thread.start{msgReader.execute()}
		
		selector = SelectorProvider.provider().openSelector()
		mainChannel = ServerSocketChannel.open()
		
		mainChannel.configureBlocking(false)

		//Bind the server to address and port
		def sockAddress = new InetSocketAddress(port)
		mainChannel.socket().bind(sockAddress)
		
		//Register server socket channel and specify we wanting to accept
		//new connections
		mainChannel.register(selector,SelectionKey.OP_ACCEPT)
	}

	//TODO limit to localhost connections unless otherwise stated
	def execute(){
		
		while(!isStopping()){
			try{
				int keys = selector.select(selectorTimeout)
				
				//TODO logging
				System.out.println("Received "+keys+" selector keys")

				processSelectionKeys(selector.selectedKeys())
				
			}catch(ex){
				//TODO logging
				ex.printStackTrace()
			}
		}
	}

	/**
	 * Used to ensure the server doesn`t run forever
	 * if a timer is used and/or stop hasn`t been requested
	 */
	def synchronized stop(){
		stop = true;
	}

	def synchronized isStopping(){
		return stop
	}

	def processSelectionKeys(def keys){
		def keyIterator = keys.iterator()
		while(keyIterator.hasNext()){
			def key = keyIterator.next()
			
			if(key.isAcceptable()){
				accept(key)
			}else if(key.isReadable()){
				msgBuilder.readMessage(key.channel())
			}

			keyIterator.remove();
		}
	}

	def accept(def key){
		def serverSocketChannel = key.channel()
		def socketChannel = serverSocketChannel.accept()
		socketChannel.configureBlocking(false)
		socketChannel.register(selector,SelectionKey.OP_READ)
	}
}
