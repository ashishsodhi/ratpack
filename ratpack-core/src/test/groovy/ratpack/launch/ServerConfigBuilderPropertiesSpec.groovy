/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.launch

import com.google.common.io.Files
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class ServerConfigBuilderPropertiesSpec extends Specification {

  @Rule
  TemporaryFolder tempFolder

  ServerConfigBuilder builder

  def setup() {
    builder = ServerConfigBuilder.noBaseDir()
  }

  def "load from properties object"() {
    given:
    Properties properties = new Properties()
    properties.put("port", "5060")

    when:
    def config = builder.props(properties).build()

    then:
    config.port == 5060
  }

  def "load from properties path"() {
    given:
    def properties = tempFolder.newFile('test.properties').toPath()
    properties << 'port=5060'

    when:
    def config = builder.props(properties).build()

    then:
    config.port == 5060
  }

  def "load from byte source"() {
    given:
    def file = tempFolder.newFile('test.properties')
    file << 'port=5060'
    def properties = Files.asByteSource(file)

    when:
    def config = builder.props(properties).build()

    then:
    config.port == 5060
  }
}
