/*
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
package org.apache.ambari.server.topology.validators;

import static org.apache.ambari.server.topology.validators.TopologyValidatorTests.topologyWithProperties;

import org.apache.ambari.server.topology.ClusterTopology;
import org.apache.ambari.server.topology.InvalidTopologyException;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class SecretReferenceValidatorTest {

  private final TopologyValidator validator = new SecretReferenceValidator();

  @Test
  public void acceptsTopologyWithoutSecretReferences() throws InvalidTopologyException {
    ClusterTopology topology = topologyWithProperties(ImmutableMap.of(
      "hdfs-site", ImmutableMap.of(
        "password", "secret"
      )
    ));

    // WHEN
    validator.validate(topology);

    // THEN
    // no exceptions expected
  }

  @Test(expected = InvalidTopologyException.class)
  public void rejectsTopologyWithSecretReferences() throws InvalidTopologyException {
    ClusterTopology topology = topologyWithProperties(ImmutableMap.of(
      "hdfs-site", ImmutableMap.of(
        "password", "SECRET:hdfs-site:1:test"
      )
    ));

    // WHEN
    validator.validate(topology);
  }

}