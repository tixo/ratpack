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
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Handler;
import ratpack.registry.Registry;
import ratpack.registry.RegistrySpec;

/**
 * A Groovy oriented handler chain builder DSL.
 * <p>
 * The methods specific to this subclass create {@link ratpack.handling.Handler} instances from closures and
 * add them to the underlying chain.
 * <p>
 * These methods are generally shortcuts for {@link #all(ratpack.handling.Handler)} on this underlying chain.
 */
public interface GroovyChain extends Chain {

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain assets(String path, String... indexFiles);

  /**
   * Creates a handler from the given closure.
   *
   * @param closure a chain definition
   * @return a new handler
   * @throws Exception any thrown by {@code closure}
   */
  Handler chain(@DelegatesTo(value = GroovyChain.class, strategy = Closure.DELEGATE_FIRST) Closure<?> closure) throws Exception;

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain delete(String path, Handler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain delete(String path, Class<? extends Handler> handler) {
    return delete(path, getRegistry().get(handler));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain delete(Handler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain delete(Class<? extends Handler> handler) {
    return delete(getRegistry().get(handler));
  }

  /**
   * Adds a {@code Handler} to this {@code GroovyChain} that delegates to the given {@code Closure} as a {@code Handler} if the
   * relative {@code path} matches the given {@code path} and the {@code request} {@code HTTPMethod} is {@code DELETE}.
   * <p>
   * See {@link GroovyChain#delete(String, ratpack.handling.Handler)} for more details.
   *
   * @param path the relative path to match on
   * @param handler the handler to delegate to
   * @return this {@code GroovyChain}
   */
  GroovyChain delete(String path, @DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler);

  /**
   * Adds a {@code Handler} to this {@code GroovyChain} that delegates to the given {@code Closure} as a {@code Handler}
   * if the {@code request} {@code HTTPMethod} is {@code DELETE} and the {@code path} is at the current root.
   * <p>
   * See {@link GroovyChain#delete(ratpack.handling.Handler)} for more details.
   *
   * @param handler the handler to delegate to
   * @return this {@code GroovyChain}
   */
  GroovyChain delete(@DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler);

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain fileSystem(String path, Action<? super Chain> action) throws Exception;

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain fileSystem(String path, Class<? extends Action<? super Chain>> action) throws Exception {
    return fileSystem(path, getRegistry().get(action));
  }

  /**
   * Creates a {@code List} of {@code Handler} from the given {@code Closure} and adds a {@code Handler} to this {@code GroovyChain} that
   * changes the {@link ratpack.file.FileSystemBinding} for the {@code Handler} list.
   * <p>
   * See {@link GroovyChain#fileSystem(String, ratpack.func.Action)} for more details.
   *
   * @param path the relative {@code path} to the new file system binding point
   * @param handlers the definition of the handler chain
   * @return this {@code GroovyChain}
   * @throws Exception any thrown by {@code closure}
   */
  GroovyChain fileSystem(String path, @DelegatesTo(value = GroovyChain.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handlers) throws Exception;

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain get(String path, Handler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain get(String path, Class<? extends Handler> handler) {
    return get(path, getRegistry().get(handler));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain get(Handler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain get(Class<? extends Handler> handler) {
    return get(getRegistry().get(handler));
  }

  /**
   * Adds a {@code Handler} to this {@code GroovyChain} that delegates to the given {@code Closure} as a {@code Handler} if the
   * relative {@code path} matches the given {@code path} and the {@code request} {@code HTTPMethod} is {@code GET}.
   * <p>
   * See {@link GroovyChain#get(String, ratpack.handling.Handler)} for more details.
   *
   * @param path the relative path to match on
   * @param handler the handler to delegate to
   * @return this {@code GroovyChain}
   */
  GroovyChain get(String path, @DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler);

  /**
   * Adds a {@code Handler} to this {@code GroovyChain} that delegates to the given {@code Closure} as a {@code Handler}
   * if the {@code request} {@code HTTPMethod} is {@code GET} and the {@code path} is at the current root.
   * <p>
   * See {@link GroovyChain#get(ratpack.handling.Handler)} for more details.
   *
   * @param handler the handler to delegate to
   * @return this {@code GroovyChain}
   */
  GroovyChain get(@DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler);

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain all(Handler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain all(Class<? extends Handler> handler) {
    return all(getRegistry().get(handler));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain path(String path, Handler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain path(Handler handler) {
    return path("", handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain path(String path, Class<? extends Handler> handler) {
    return path(path, getRegistry().get(handler));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain path(Class<? extends Handler> handler) {
    return path("", handler);
  }

  /**
   * Adds a {@code Handler} to this {@code GroovyChain} that delegates to the given {@code Closure} as a {@code Handler} if the
   * relative {@code path} matches the given {@code path} exactly.
   * <p>
   * See {@link GroovyChain#path(String, ratpack.handling.Handler)} for more details.
   *
   * @param path the relative path to match exactly on
   * @param handler the handler to delegate to
   * @return this {@code GroovyChain}
   */
  GroovyChain path(String path, @DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler);

  default GroovyChain path(@DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler) {
    return path("", handler);
  }

  /**
   * Adds the given {@code Closure} as a {@code Handler} to this {@code GroovyChain}.
   *
   * @param handler the {@code Closure} to add
   * @return this {@code GroovyChain}
   */
  GroovyChain all(@DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler);

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain header(String headerName, String headerValue, Handler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain header(String headerName, String headerValue, Class<? extends Handler> handler) {
    return header(headerName, headerValue, getRegistry().get(handler));
  }

  /**
   * Adds a {@code Handler} to this {@code GroovyChain} that delegates to the given {@code Closure} as a {@code Handler} if the {@code request}
   * has a {@code HTTPHeader} with the given name and a it's value matches the given value exactly.
   * <p>
   * See {@link GroovyChain#header(String, String, ratpack.handling.Handler)} for more details.
   *
   * @param headerName the name of the HTTP Header to match on
   * @param headerValue the value of the HTTP Header to match on
   * @param handler the handler to delegate to
   * @return this {@code GroovyChain}
   */
  GroovyChain header(String headerName, String headerValue, @DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler);

  /**
   * {@inheritDoc}
   */
  GroovyChain host(String hostName, Action<? super Chain> action) throws Exception;

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain host(String hostName, Class<? extends Action<? super Chain>> action) throws Exception {
    return host(hostName, getRegistry().get(action));
  }

  /**
   * If the request has a {@code Host} header that matches the given host name exactly, handling will be delegated to the chain defined by the given closure.
   *
   * @param hostName the name of the HTTP Header to match on
   * @param handler the handler to delegate to
   * @return this {@code GroovyChain}
   * @throws Exception any thrown by {@code closure}
   * @see #host(String, ratpack.func.Action)
   */
  GroovyChain host(String hostName, @DelegatesTo(value = GroovyChain.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler) throws Exception;

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain insert(Action<? super Chain> action) throws Exception;

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain insert(Class<? extends Action<? super Chain>> action) throws Exception {
    return insert(getRegistry().get(action));
  }

  /**
   * Inserts the given nested handler chain.
   * <p>
   * Shorter form of {@link #all(Handler)} handler}({@link #chain(groovy.lang.Closure) chain}({@code closure}).
   *
   * @param closure the handler chain to insert
   * @return this
   * @throws Exception any thrown by {@code closure}
   */
  GroovyChain insert(@DelegatesTo(value = GroovyChain.class, strategy = Closure.DELEGATE_FIRST) Closure<?> closure) throws Exception;

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain patch(String path, Handler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain patch(String path, Class<? extends Handler> handler) {
    return patch(path, getRegistry().get(handler));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain patch(Handler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain patch(Class<? extends Handler> handler) {
    return patch(getRegistry().get(handler));
  }

  /**
   * Adds a {@code Handler} to this {@code GroovyChain} that delegates to the given {@code Closure} as a {@code Handler} if the
   * relative {@code path} matches the given {@code path} and the {@code request} {@code HTTPMethod} is {@code PATCH}.
   * <p>
   * See {@link GroovyChain#put(String, ratpack.handling.Handler)} for more details.
   *
   * @param path the relative path to match on
   * @param handler the handler to delegate to
   * @return this {@code GroovyChain}
   */
  GroovyChain patch(String path, @DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler);

  /**
   * Adds a {@code Handler} to this {@code GroovyChain} that delegates to the given {@code Closure} as a {@code Handler}
   * if the {@code request} {@code HTTPMethod} is {@code PATCH} and the {@code path} is at the current root.
   * <p>
   * See {@link GroovyChain#put(ratpack.handling.Handler)} for more details.
   *
   * @param handler the handler to delegate to
   * @return this {@code GroovyChain}
   */
  GroovyChain patch(@DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler);

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain post(String path, Handler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain post(String path, Class<? extends Handler> handler) {
    return post(path, getRegistry().get(handler));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain post(Handler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain post(Class<? extends Handler> handler) {
    return patch(getRegistry().get(handler));
  }

  /**
   * Adds a {@code Handler} to this {@code GroovyChain} that delegates to the given {@code Closure} as a {@code Handler} if the
   * relative {@code path} matches the given {@code path} and the {@code request} {@code HTTPMethod} is {@code POST}.
   * <p>
   * See {@link GroovyChain#post(String, ratpack.handling.Handler)} for more details.
   *
   * @param path the relative path to match on
   * @param handler the handler to delegate to
   * @return this {@code GroovyChain}
   */
  GroovyChain post(String path, @DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler);

  /**
   * Adds a {@code Handler} to this {@code GroovyChain} that delegates to the given {@code Closure} as a {@code Handler}
   * if the {@code request} {@code HTTPMethod} is {@code POST} and the {@code path} is at the current root.
   * <p>
   * See {@link GroovyChain#post(ratpack.handling.Handler)} for more details.
   *
   * @param handler the handler to delegate to
   * @return this {@code GroovyChain}
   */
  GroovyChain post(@DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler);

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain prefix(String prefix, Action<? super Chain> action) throws Exception;

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain prefix(String prefix, Class<? extends Action<? super Chain>> action) throws Exception {
    return prefix(prefix, getRegistry().get(action));
  }

  /**
   * Creates a {@code List} of {@code Handler} from the given {@code Closure} and adds a {@code Handler} to
   * this {@code GroovyChain} that delegates to the {@code Handler} list if the relative path starts with the given
   * {@code prefix}.
   * <p>
   * See {@link Chain#prefix(String, ratpack.func.Action)} for more details.
   *
   * @param prefix the relative path to match on
   * @param chain the definition of the chain to delegate to
   * @return this {@code GroovyChain}
   * @throws Exception any exception thrown by the given closure
   */
  GroovyChain prefix(String prefix, @DelegatesTo(value = GroovyChain.class, strategy = Closure.DELEGATE_FIRST) Closure<?> chain) throws Exception;

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain put(String path, Handler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain put(String path, Class<? extends Handler> handler) {
    return put(path, getRegistry().get(handler));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain put(Handler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain put(Class<? extends Handler> handler) {
    return put(getRegistry().get(handler));
  }

  /**
   * Adds a {@code Handler} to this {@code GroovyChain} that delegates to the given {@code Closure} as a {@code Handler} if the
   * relative {@code path} matches the given {@code path} and the {@code request} {@code HTTPMethod} is {@code PUT}.
   * <p>
   * See {@link GroovyChain#put(String, ratpack.handling.Handler)} for more details.
   *
   * @param path the relative path to match on
   * @param handler the handler to delegate to
   * @return this {@code GroovyChain}
   */
  GroovyChain put(String path, @DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler);

  /**
   * Adds a {@code Handler} to this {@code GroovyChain} that delegates to the given {@code Closure} as a {@code Handler}
   * if the {@code request} {@code HTTPMethod} is {@code PUT} and the {@code path} is at the current root.
   * <p>
   * See {@link GroovyChain#put(ratpack.handling.Handler)} for more details.
   *
   * @param handler the handler to delegate to
   * @return this {@code GroovyChain}
   */
  GroovyChain put(@DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler);

  /**
   * {@inheritDoc}
   */
  GroovyChain redirect(int code, String location);

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain register(Registry registry);

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain register(Action<? super RegistrySpec> action) throws Exception;

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain register(Registry registry, Action<? super Chain> action) throws Exception;

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain register(Registry registry, Class<? extends Action<? super Chain>> action) throws Exception {
    return register(registry, getRegistry().get(action));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  GroovyChain register(Action<? super RegistrySpec> registryAction, Action<? super Chain> chainAction) throws Exception;

  /**
   * {@inheritDoc}
   */
  @Override
  default GroovyChain register(Action<? super RegistrySpec> registryAction, Class<? extends Action<? super Chain>> action) throws Exception {
    return register(registryAction, getRegistry().get(action));
  }

  GroovyChain register(Action<? super RegistrySpec> registryAction, @DelegatesTo(value = GroovyChain.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler) throws Exception;

  GroovyChain register(Registry registry, @DelegatesTo(value = GroovyChain.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handlers) throws Exception;

  GroovyChain register(@DelegatesTo(value = RegistrySpec.class, strategy = Closure.DELEGATE_FIRST) Closure<?> closure) throws Exception;

}
