
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ambari.server.events.listeners.requests;

import org.apache.ambari.server.events.AmbariUpdateEvent;
import org.apache.ambari.server.events.publishers.StateUpdateEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Injector;

public class StateUpdateListener {

  private final static Logger LOG = LoggerFactory.getLogger(StateUpdateListener.class);

  @Autowired
  SimpMessagingTemplate simpMessagingTemplate;

  public StateUpdateListener(Injector injector) {
    StateUpdateEventPublisher stateUpdateEventPublisher =
      injector.getInstance(StateUpdateEventPublisher.class);
    stateUpdateEventPublisher.register(this);
  }

  @Subscribe
  @AllowConcurrentEvents
  public void onUpdateEvent(AmbariUpdateEvent event) {
    LOG.debug("Received status update event {}", event.toString());
    simpMessagingTemplate.convertAndSend(event.getDestination(), event);
  }
}
