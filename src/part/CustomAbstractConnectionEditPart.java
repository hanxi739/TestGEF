package part;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;

import policies.CustomConnectionEditPolicy;
import policies.CustomConnectionEndpointEditPolicy;

public class CustomAbstractConnectionEditPart extends AbstractConnectionEditPart{

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ROLE,new CustomConnectionEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,new CustomConnectionEndpointEditPolicy());

	}

}
