package com.easyb.report.server;

import java.nio.ByteBuffer;

/**
 * Writes messages received from a socket and 
 * puts them on the message queue
 */
//TODO could possibly make a worker thread but probably not worth
//extra overhead of creating and starting,will need to test performance 
//on large amounts of behaviours	
public class MessageBuilder {
	private static final int TYPE_POS = 0
	private static final int ID_SIZE_POS = 4
	private static final int MSG_SIZE_POS = 8
	private static final int ID_POS = 12
	def messageQueue
	//TODO byte buffer size may need to change 
	private def buffer = ByteBuffer.allocate(4096);
	/*ByteBuffer incomingLengthInBytes =
		ByteBuffer.allocate(4);
//		 size of an 'int'
		socket.read(incomingLengthInBytes);
		incomingLengthInBytes.rewind();
		int incomingLength =
		incomingLengthInBytes.getInt();
		System.out.println("Got Incoming
		Length as: "+incomingLength+"
		bytes");

//		 now allocate the correct size for
//		 the message...
		ByteBuffer incomingData = ByteBuffer
		.allocate(incomingLength);
		socket.read(incomingData);
		incomingData.rewind();
		String string = incomingData
		.asCharBuffer().toString();*/
		
	def readMessage(socketChannel){

		buffer.clear()

		int count = 0
		while((count=socketChannel.read(buffer))>0){
			messageQueue.addMessage(getMessage())
		}

		if(count<0){
			//Close channel on EOf invalidates 
			//the key
			socketChannel.close()
		}
	}

	def getMessage(){
		buffer.flip() //makes the buffer readable
		
		//First value is message type which is a int
		int type = buffer.getInt(TYPE_POS)
		
		//Next value is also an int which is the id size
		int idSize = buffer.getInt(ID_SIZE_POS)
		
		//Next value is also an int which is the length 
		//of the message
		int msgSize = buffer.getInt(MSG_SIZE_POS)
		
		//Next its the id itself which is a string 
		byte[] arrIdBytes = new byte[idSize]
		buffer.get(arrIdBytes,ID_POS,idSize)
		
		//Finally get the message
		byte[] arrMsgBytes = new byte[msgSize]
		buffer.get(arrMsgBytes,ID_POS+idSize,msgSize)

		buffer.clear()
					
		return new Message(
				type:MessageTypeEnum.parseInt(type),
				behaviourName:new String(arrIdBytes),
				message:new String(arrMsgBytes))
	}
	
}
