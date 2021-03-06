/*
 * Copyright 2012-2014 TORCH GmbH
 *
 * This file is part of Graylog2.
 *
 * Graylog2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog2.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.graylog2.shared.initializers;

import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager.Listener;
import org.graylog2.plugin.lifecycles.Lifecycle;
import org.graylog2.shared.ServerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * @author Dennis Oelkers <dennis@torch.sh>
 */
public class ServiceManagerListener implements Listener {
    private final Logger LOG = LoggerFactory.getLogger(ServiceManagerListener.class);
    private final ServerStatus serverStatus;

    @Inject
    public ServiceManagerListener(ServerStatus serverStatus) {
        this.serverStatus = serverStatus;
    }

    @Override
    public void healthy() {
        LOG.info("Services are healthy");
        serverStatus.setLifecycle(Lifecycle.RUNNING);
    }

    @Override
    public void stopped() {
        LOG.info("Services are now stopped.");
    }

    @Override
    public void failure(Service service) {
        LOG.info("Service failure for service {}", service);
        serverStatus.setLifecycle(Lifecycle.FAILED);
    }
}
