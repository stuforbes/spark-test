package uk.co.stuforbes.sparktest.fake;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import spark.Request;

import com.google.common.collect.Maps;

public class FakeRequestBuilder {
	private final Map<String, Object> params;
	private final Map<String, Object> attrs;

	private HttpServletRequest raw = null;

	public static FakeRequestBuilder newInstance() {
		return new FakeRequestBuilder();
	}

	private FakeRequestBuilder() {
		this.params = Maps.newHashMap();
		this.attrs = Maps.newHashMap();
	}

	public FakeRequestBuilder withParam(final String key, final String value) {
		params.put(key, value);
		return this;
	}

	public FakeRequestBuilder withAttribute(final String key, final String value) {
		attrs.put(key, value);
		return this;
	}

	public FakeRequestBuilder withRaw(final HttpServletRequest httpRequest) {
		this.raw = httpRequest;
		return this;
	}

	public Request build() {
		return new FakeRequest(params, attrs, raw);
	}

	private static class FakeRequest extends Request {

		private final Map<String, Object> params;
		private final Map<String, Object> attrs;
		private final HttpServletRequest raw;

		public FakeRequest(final Map<String, Object> params,
				final Map<String, Object> attrs, final HttpServletRequest raw) {
			super();
			this.params = params;
			this.attrs = attrs;
			this.raw = raw;
		}

		@Override
		public Set<String> attributes() {
			return attrs.keySet();
		}

		@Override
		public Object attribute(final String attribute) {
			return attrs.get(attribute);
		}

		@Override
		public void attribute(final String attribute, final Object value) {
			attrs.put(attribute, value);
		}

		@Override
		public Set<String> queryParams() {
			return params.keySet();
		}

		@Override
		public String queryParams(final String queryParam) {
			return (String) params.get(queryParam);
		}

		@Override
		public HttpServletRequest raw() {
			return raw;
		}
	}
}
