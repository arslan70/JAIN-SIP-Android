package com.example.sipmessagetest;

import java.text.ParseException;
import java.util.*;

import com.telestax.tavax.sip.SipStackExt;
import com.telestax.tavax.sip.clientauthutils.AuthenticationHelper;
import com.telestax.tavax.sip.header.extensions.ReplacesHeader;

import tavax.sip.ClientTransaction;
import tavax.sip.Dialog;
import tavax.sip.DialogTerminatedEvent;
import tavax.sip.IOExceptionEvent;
import tavax.sip.InvalidArgumentException;
import tavax.sip.ListeningPoint;
import tavax.sip.PeerUnavailableException;
import tavax.sip.RequestEvent;
import tavax.sip.ResponseEvent;
import tavax.sip.ServerTransaction;
import tavax.sip.SipException;
import tavax.sip.SipFactory;
import tavax.sip.SipListener;
import tavax.sip.SipProvider;
import tavax.sip.SipStack;
import tavax.sip.TimeoutEvent;
import tavax.sip.TransactionTerminatedEvent;
import tavax.sip.TransactionUnavailableException;
import tavax.sip.address.Address;
import tavax.sip.address.AddressFactory;
import tavax.sip.address.SipURI;
import tavax.sip.address.URI;
import tavax.sip.header.AuthorizationHeader;
import tavax.sip.header.CSeqHeader;
import tavax.sip.header.CallIdHeader;
import tavax.sip.header.ContactHeader;
import tavax.sip.header.ContentTypeHeader;
import tavax.sip.header.ExpiresHeader;
import tavax.sip.header.FromHeader;
import tavax.sip.header.Header;
import tavax.sip.header.HeaderFactory;
import tavax.sip.header.MaxForwardsHeader;
import tavax.sip.header.RecordRouteHeader;
import tavax.sip.header.RouteHeader;
import tavax.sip.header.SupportedHeader;
import tavax.sip.header.ToHeader;
import tavax.sip.header.ViaHeader;
import tavax.sip.message.MessageFactory;
import tavax.sip.message.Request;
import tavax.sip.message.Response;
import android.os.AsyncTask;

public class SipSendMessage extends AsyncTask<Object, Object, Object>  {

	private void sendMessage(String to, String message) throws ParseException,
			InvalidArgumentException, SipException {

		SipStackAndroid.getInstance();
		SipURI from = SipStackAndroid.addressFactory.createSipURI(SipStackAndroid.getInstance().sipUserName, SipStackAndroid.getInstance().localEndpoint);
		SipStackAndroid.getInstance();
		Address fromNameAddress = SipStackAndroid.addressFactory.createAddress(from);
		SipStackAndroid.getInstance();
		// fromNameAddress.setDisplayName(sipUsername);
		FromHeader fromHeader = SipStackAndroid.headerFactory.createFromHeader(fromNameAddress,
				"Tzt0ZEP92");

		// String username = to.substring(to.indexOf(":") + 1, to.indexOf("@"));
		// String address = to.substring(to.indexOf("@") + 1);

		SipStackAndroid.getInstance();
		URI toAddress = SipStackAndroid.addressFactory.createURI(to);
		SipStackAndroid.getInstance();
		Address toNameAddress = SipStackAndroid.addressFactory.createAddress(toAddress);
		SipStackAndroid.getInstance();
		// toNameAddress.setDisplayName(username);
		ToHeader toHeader = SipStackAndroid.headerFactory.createToHeader(toNameAddress, null);

		SipStackAndroid.getInstance();
		URI requestURI = SipStackAndroid.addressFactory.createURI(to);
		// requestURI.setTransportParam("udp");

		ArrayList<ViaHeader> viaHeaders = createViaHeader();

		SipStackAndroid.getInstance();
		CallIdHeader callIdHeader = SipStackAndroid.sipProvider.getNewCallId();

		SipStackAndroid.getInstance();
		CSeqHeader cSeqHeader = SipStackAndroid.headerFactory.createCSeqHeader(50l,
				Request.MESSAGE);

		SipStackAndroid.getInstance();
		MaxForwardsHeader maxForwards = SipStackAndroid.headerFactory
				.createMaxForwardsHeader(70);

		SipStackAndroid.getInstance();
		Request request = SipStackAndroid.messageFactory.createRequest(requestURI,
				Request.MESSAGE, callIdHeader, cSeqHeader, fromHeader,
				toHeader, viaHeaders, maxForwards);
		SipStackAndroid.getInstance();
		SupportedHeader supportedHeader = SipStackAndroid.headerFactory
				.createSupportedHeader("replaces, outbound");
		request.addHeader(supportedHeader);

		SipStackAndroid.getInstance();
		SipURI routeUri = SipStackAndroid.addressFactory.createSipURI(null, SipStackAndroid.getInstance().remoteIp);
		routeUri.setTransportParam(SipStackAndroid.transport);
		routeUri.setLrParam();
		routeUri.setPort(SipStackAndroid.remotePort);

		SipStackAndroid.getInstance();
		Address routeAddress = SipStackAndroid.addressFactory.createAddress(routeUri);
		SipStackAndroid.getInstance();
		RouteHeader route =SipStackAndroid.headerFactory.createRouteHeader(routeAddress);
		request.addHeader(route);
		SipStackAndroid.getInstance();
		ContentTypeHeader contentTypeHeader = SipStackAndroid.headerFactory
				.createContentTypeHeader("text", "plain");
		request.setContent(message, contentTypeHeader);
		System.out.println(request);
		SipStackAndroid.getInstance();
		ClientTransaction transaction = SipStackAndroid.sipProvider
				.getNewClientTransaction(request);
		// Send the request statefully, through the client transaction.
		transaction.sendRequest();
	}




	private Address createContactAddress() {
		try {
			SipStackAndroid.getInstance();
			return SipStackAndroid.addressFactory.createAddress("sip:"
					+ SipStackAndroid.getInstance().sipUserName + "@"
					+ SipStackAndroid.getInstance().localEndpoint
					+ ";transport=udp" + ";registering_acc=23_23_228_238");
		} catch (ParseException e) {
			return null;
		}
	}

	private ArrayList<ViaHeader> createViaHeader() {
		ArrayList<ViaHeader> viaHeaders = new ArrayList<ViaHeader>();
		ViaHeader myViaHeader;
		try {
			SipStackAndroid.getInstance();
			SipStackAndroid.getInstance();
			myViaHeader = SipStackAndroid.headerFactory.createViaHeader(
					SipStackAndroid.localIp, SipStackAndroid.localPort,
					SipStackAndroid.transport, null);
			myViaHeader.setRPort();
			viaHeaders.add(myViaHeader);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return viaHeaders;

	}



	@Override
	protected Object doInBackground(Object... params) {
		try {
			String to = (String) params[0];
			String message = (String) params[1];

			sendMessage(to, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}