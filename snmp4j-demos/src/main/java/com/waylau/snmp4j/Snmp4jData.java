/**
 * 
 */
package com.waylau.snmp4j;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年10月23日 
 */
public class Snmp4jData {

	//下面只是一个方法  
	 public static String getIpOfGateway(){  
	        String gatewayIpString=null; //网关ip地址是这个字符串的子串  
	        String gatewayIp=null;  // 这是代表网关ip  
	        try {  
	             
	            /** 
	             * Set properties of target 
	             */  
	            CommunityTarget localhost = new CommunityTarget();  
	            Address address = GenericAddress.parse("udp:127.0.0.1/161");  
	            localhost.setAddress(address);  
	            localhost.setCommunity(new OctetString("public"));    
	            localhost.setRetries(2);   
	            localhost.setTimeout(5*60);   
	            localhost.setVersion(SnmpConstants.version2c);  
	            /** 
	             * Set protocols of UDP and SNMP 
	             */  
	            TransportMapping transport = new DefaultUdpTransportMapping();   
	            transport.listen();  
	            Snmp protocol = new Snmp(transport);  
	            /** 
	             * OID binding 
	             */  
	            PDU requestPDU = new PDU();  
	            requestPDU.add(new VariableBinding(new OID("1.3.6.1.2.1.4.21.1.7")));//ipRouteNextHop  
	            requestPDU.setType(PDU.GETNEXT);  
	            /** 
	             * 
	             */  
	            ResponseEvent responseEvent = protocol.send(requestPDU, localhost);  
	            PDU responsePDU=responseEvent.getResponse();  
	            if(responsePDU!=null){  
	                VariableBinding getIp=responsePDU.get(0);  
	                gatewayIpString=getIp.toString();  
	            }  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	        gatewayIp=gatewayIpString.substring(31);  
	        return gatewayIp;  
	         
	    } 
}
