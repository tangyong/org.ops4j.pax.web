/* Copyright 2007 Alin Dreghiciu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.web.service.internal;

import java.util.Dictionary;
import java.util.EventListener;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.NamespaceException;

public class HttpServiceProxy
    implements StoppableHttpService
{

    private static final Log LOG = LogFactory.getLog( HttpServiceProxy.class );
    private StoppableHttpService m_delegate;

    public HttpServiceProxy( final StoppableHttpService delegate )
    {
        Assert.notNull( "state == null", delegate );
        m_delegate = delegate;
    }

    public void registerServlet(
        final String alias,
        final Servlet servlet,
        final Dictionary initParams,
        final HttpContext httpContext )
        throws ServletException, NamespaceException
    {
        LOG.info( "Registering servlet: [" + alias + "] -> " + servlet );
        m_delegate.registerServlet( alias, servlet, initParams, httpContext );
    }

    public void registerResources(
        final String alias,
        final String name,
        final HttpContext httpContext )
        throws NamespaceException
    {
        LOG.info( "Registering resource: [" + alias + "] -> " + name );
        m_delegate.registerResources( alias, name, httpContext );
    }

    public void unregister( final String alias )
    {
        LOG.info( "Unregistering [" + alias + "]" );
        m_delegate.unregister( alias );
    }

    public HttpContext createDefaultHttpContext()
    {
        LOG.info( "Creating adefault context" );
        return m_delegate.createDefaultHttpContext();
    }

    public synchronized void stop()
    {
        LOG.info( "Stopping http service: [" + this + "]" );
        m_delegate.stop();
        m_delegate = new StoppedHttpService();
    }

    public void registerEventListener( final EventListener listener )
    {
        LOG.info( "Registering event listener [" + listener + "]" );
        m_delegate.registerEventListener( listener );
    }

    public void unregisterEventListener( final EventListener listener )
    {
        LOG.info( "Unregistering event listener [" + listener + "]" );
        m_delegate.unregisterEventListener( listener );
    }
}