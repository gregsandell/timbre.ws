package net.l8thStreet.sharc.service;

import net.l8thStreet.sharc.exceptions.SharcException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sandelgb
 * Date: Jul 6, 2007
 * Time: 2:07:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ServiceInterface {
	public String getAction();

  public List<String> getRequiredParamList();
  public List<String> getAlternateParamList();
  public void doPlot(HttpServletRequest request, HttpServletResponse response) throws SharcException;
  public void plot(HttpServletRequest request, HttpServletResponse response) throws SharcException;
	public String requirements(HttpServletRequest request);
	public void validateService(HttpServletRequest request) throws SharcException;
}
