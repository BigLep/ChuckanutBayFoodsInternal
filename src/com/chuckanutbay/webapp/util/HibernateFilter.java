package com.chuckanutbay.webapp.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuckanutbay.businessobjects.util.HibernateUtil;


/**
 * A servlet {@link Filter} that opens and closes a Hibernate {@link Session} for each request.
 * Use this {@link Filter} for the <b>session-per-request</b> pattern and if you are using <i>Detached Objects</i>.
 * This filter guarantees a sane state, committing any pending database transaction once the {@link FilterChain} has executed.
 * If there is an error in the {@link FilterChain}, the transaction will be rolled back.
 * {@link Exception}s are handled no further, but rather passed up the chain so the employed error handling strategy can take care of them.
 * Regardless of whether the request was successful or not, the Hibernate {@link Session} of the current thread will be closed by the end of the response to the client.
 * @see HibernateUtil
 * @see <a href="http://www.hibernate.org/43.html">Hibernate documentation</a>
 */
public class HibernateFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(HibernateFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("Servlet filter init, now opening/closing a Session for each request.");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			HibernateUtil.beginTransaction();
			chain.doFilter(request, response);
			HibernateUtil.commitTransaction();
		} catch(IOException e) {
			HibernateUtil.rollbackTransaction();
			throw e;
		} catch(ServletException e) {
			HibernateUtil.rollbackTransaction();
			throw e;
		} catch(RuntimeException e) {
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			// No matter what happens, close the Session.
			HibernateUtil.closeSession();
		}
	}

	@Override
	public void destroy() {
		HibernateUtil.closeSession();
	}
}
