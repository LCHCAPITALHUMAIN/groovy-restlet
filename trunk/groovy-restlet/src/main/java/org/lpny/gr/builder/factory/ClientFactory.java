/**
 * 
 */
package org.lpny.gr.builder.factory;

import groovy.util.FactoryBuilderSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.restlet.Client;
import org.restlet.data.Protocol;

/**
 * Shortcut factory to create {@link Client}
 * 
 * @author keke
 * @reversion $Revision$
 * @version 0.1
 * @since 0.1.0
 */
public class ClientFactory extends RestletFactory {
    /**
     * <b>Attribute</b>: {@link Protocol} instance used to create client.
     * 
     * @see Client#Client(org.restlet.Context, Protocol)
     */
    protected static final String PROTOCOL  = "protocol";
    /**
     * <b>Attribute</b>: List of {@link Protocol} instances used to create
     * client.
     * 
     * @see Client#Client(org.restlet.Context, List))
     */
    protected static final String PROTOCOLS = "protocols";

    protected static List<Protocol> extractProtocols(final Object value,
            final Map attributes) {
        final List<Protocol> protocols = new ArrayList<Protocol>();
        Protocol protocol = (Protocol) attributes.remove(PROTOCOL);
        if (protocol == null) {
            if (value != null && value instanceof Protocol) {
                protocol = (Protocol) value;
            }
        }
        if (protocol != null) {
            protocols.add(protocol);
        }
        final List<Protocol> list = (List<Protocol>) attributes
                .remove(PROTOCOLS);
        if (list != null) {
            protocols.addAll(list);
        }
        return protocols;
    }

    /**
     * 
     */
    public ClientFactory() {
        super();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object newInstanceInner(final FactoryBuilderSupport builder,
            final Object name, final Object value, final Map attributes)
            throws InstantiationException, IllegalAccessException {
        final List<Protocol> protocols = extractProtocols(value, attributes);
        return new Client(getRestletContext(builder), protocols);
    }

}