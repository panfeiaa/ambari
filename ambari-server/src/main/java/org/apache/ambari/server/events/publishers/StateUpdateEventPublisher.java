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
package org.apache.ambari.server.events.publishers;

import java.util.concurrent.Executors;

import org.apache.ambari.server.events.AmbariUpdateEvent;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.inject.Singleton;

@Singleton
public final class StateUpdateEventPublisher {

  private final EventBus m_eventBus;

  public StateUpdateEventPublisher() {
    m_eventBus = new AsyncEventBus("ambari-update-bus",
      Executors.newSingleThreadExecutor());
  }

  public void publish(AmbariUpdateEvent event) {
    m_eventBus.post(event);
  }

  public void register(Object object) {
    m_eventBus.register(object);
  }
}
