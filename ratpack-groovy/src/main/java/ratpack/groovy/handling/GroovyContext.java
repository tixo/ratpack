/*
 * Copyright 2013 the original author or authors.
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

package ratpack.groovy.handling;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import ratpack.handling.ByContentSpec;
import ratpack.handling.ByMethodSpec;
import ratpack.handling.Context;
import ratpack.handling.RequestOutcome;

/**
 * Subclass of {@link ratpack.handling.Context} that adds Groovy friendly variants of methods.
 */
public interface GroovyContext extends Context {

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyContext getContext();

  /**
   * Groovy friendly overload of {@link #byMethod(ratpack.func.Action)}.
   *
   * <pre class="tested-dynamic">
   * import ratpack.groovy.test.handling.GroovyRequestFixture
   * import static ratpack.groovy.Groovy.groovyHandler
   *
   * def handler = groovyHandler {
   *   byMethod {
   *     def message = "hello!"
   *     get {
   *       render "$message from GET request"
   *     }
   *     post {
   *       render "$message from POST request"
   *     }
   *   }
   * }
   *
   * def result = GroovyRequestFixture.handle(handler) {
   *   method "get"
   * }
   *
   * assert result.rendered(CharSequence) == "hello! from GET request"
   *
   * result = GroovyRequestFixture.handle(handler) {
   *   method "post"
   * }
   *
   * assert result.rendered(CharSequence) == "hello! from POST request"
   * </pre>
   *
   * @param closure defines the action to take for different HTTP methods
   * @throws Exception any thrown by the closure
   */
  void byMethod(@DelegatesTo(value = ByMethodSpec.class, strategy = Closure.DELEGATE_FIRST) Closure<?> closure) throws Exception;

  /**
   * Groovy friendly overload of {@link #byContent(ratpack.func.Action)}.
   *
   * <pre class="tested-dynamic">{@code
   * import ratpack.groovy.test.handling.GroovyRequestFixture
   * import static ratpack.groovy.Groovy.groovyHandler
   *
   * def handler = groovyHandler {
   *   byContent {
   *     def message = "hello!"
   *     json {
   *       render "{\"msg\": \"$message\"}"
   *     }
   *     html {
   *       render "<p>$message</p>"
   *     }
   *   }
   * }
   *
   * def result = GroovyRequestFixture.handle(handler) {
   *   header("Accept", "application/json");
   * }
   *
   * assert result.rendered(CharSequence) == "{\"msg\": \"hello!\"}"
   * assert result.headers.get("content-type") == "application/json"
   *
   * result = GroovyRequestFixture.handle(handler) {
   *   header("Accept", "text/plain; q=1.0, text/html; q=0.8, application/json; q=0.7");
   * }
   *
   * assert result.rendered(CharSequence) == "<p>hello!</p>";
   * assert result.headers.get("content-type") == "text/html";
   * }</pre>
   *
   * @param closure defines the action to take for the different content types
   * @throws Exception any thrown by the closure
   */
  void byContent(@DelegatesTo(value = ByContentSpec.class, strategy = Closure.DELEGATE_FIRST) Closure<?> closure) throws Exception;

  /**
   * Adds a request close handler.
   *
   * @param closure A closure to call when the request is closed
   */
  void onClose(@DelegatesTo(value = RequestOutcome.class, strategy = Closure.DELEGATE_FIRST) Closure<?> closure);

}
