package com.easyb.report.server;

public enum MessageTypeEnum {
	MESSAGE(0),
	REPORT(1)

	def val
	MessageTypeEnum(val){this.val= val}

	def int value(){return val}
	
	def static parseInt(int val){
		switch(val){
		case 0:return MESSAGE
		case 1:return REPORT
		default:throw new MessageException("Unknown message type $val")
		}
	}
}
