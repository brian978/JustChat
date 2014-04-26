package com.acamar.net.websocket;

import com.acamar.net.ConnectionException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class Connection extends com.acamar.net.Connection
{
    @Override
    protected String getConfigFilename()
    {
        return null;
    }

    @Override
    public void connect() throws ConnectionException
    {

    }
    //    protected Config config = null;
    //    protected Session session = null;
    //    protected Endpoint endpoint = new ConnectionEndoint();
    //
    //    public Connection()
    //    {
    //        setup(getOption("protocol", "ws"), getOption("host"), Integer.parseInt(getOption("port")));
    //    }
    //
    //    @Override
    //    protected String getConfigFilename()
    //    {
    //        return null;
    //    }
    //
    //    public void connect()
    //    {
    //        try {
    //            session = ContainerProvider.getWebSocketContainer().connectToServer(
    //                    endpoint,
    //                    ClientEndpointConfig.Builder.create().build(),
    //                    URI.create(protocol + "://" + host + ":" + port)
    //            );
    //        } catch (DeploymentException | IOException e) {
    //            fireConnectionEvent(e.getCause().getMessage(), ConnectionEvent.ERROR_OCCURED);
    //        }
    //    }
    //
    //    public void disconnect() throws ConnectionException
    //    {
    //        super.disconnect();
    //
    //        if (session != null) {
    //            try {
    //                session.close();
    //            } catch (IOException e) {
    //                throw new ConnectionException(e);
    //            }
    //        }
    //    }
    //
    //    public void send(String message)
    //    {
    //        if (session != null && session.isOpen()) {
    //            session.getAsyncRemote().sendText(message);
    //        }
    //    }
    //
    //    protected void fireConnectionEvent(String message, Session session, int statusCode)
    //    {
    //        EventManager.fireEvent(
    //                ConnectionStatusListener.class,
    //                new SocketConnectionEvent(message, session, statusCode),
    //                new FireEventCallback()
    //                {
    //                    @Override
    //                    public void fireEvent(Object listener, EventInterface e)
    //                    {
    //                        ((ConnectionStatusListener) listener).statusChanged((SocketConnectionEvent) e);
    //                    }
    //                }
    //        );
    //    }
    //
    //    /**
    //     * Client endpoint for the current connection
    //     */
    //    private class ConnectionEndoint extends Endpoint
    //    {
    //        @Override
    //        public void onOpen(Session session, EndpointConfig endpointConfig)
    //        {
    //            System.out.println("Opened connection to server");
    //
    //            fireConnectionEvent("Connection OK", session, SocketConnectionEvent.CONNECTION_OPENED);
    //        }
    //
    //        @OnError
    //        public void onError(Session session, Throwable t)
    //        {
    //            t.printStackTrace();
    //
    //            fireConnectionEvent(t.getMessage(), session, SocketConnectionEvent.ERROR_OCCURED);
    //        }
    //
    //        @OnMessage
    //        public void onMessage(final String message)
    //        {
    //            EventManager.fireEvent(SocketMessageListener.class, new MessageEvent(message), new FireEventCallback()
    //            {
    //                @Override
    //                public void fireEvent(Object listener, EventInterface e)
    //                {
    //                    ((SocketMessageListener) listener).processMessage(message);
    //                }
    //            });
    //        }
    //
    //        @OnClose
    //        public void onClose(Session session, CloseReason closeReason)
    //        {
    //            System.out.println("Closed connection to server because " + closeReason.getReasonPhrase());
    //
    //            fireConnectionEvent(closeReason.getReasonPhrase(), session, SocketConnectionEvent.CONNECTION_CLOSED);
    //
    //            try {
    //                config.save();
    //            } catch (IOException e) {
    //                // We will handle this using an event
    //                e.printStackTrace();
    //            }
    //        }
    //    }
}
