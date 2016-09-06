package org.openmrs.module.isantepluspatientdashboard.fragment.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.IsantePlusVital;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class IsantePlusMostRecentVitalsFragmentController {
	protected final Log log = LogFactory.getLog(getClass());

	private Double calculateLatestBMI(Patient patient) {
		Obs height = Context.getService(IsantePlusPatientDashboardService.class).getLatestHeightForPatient(patient);
		Obs weight = Context.getService(IsantePlusPatientDashboardService.class).getLatestWeightForPatient(patient);

		if (height != null & weight != null) {
			return Context.getService(IsantePlusPatientDashboardService.class)
					.roundAbout(weight.getValueNumeric() / (Math.pow(height.getValueNumeric() * 0.01, 2)), 1);
		}
		return null;
	}

	public void controller(FragmentModel model, @FragmentParam("patientId") Patient patient) {
		IsantePlusVital height = new IsantePlusVital(
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.vitals.height.label"),
				Context.getService(IsantePlusPatientDashboardService.class).getLatestHeightForPatient(patient),
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.vitals.height.unit"));
		IsantePlusVital weight = new IsantePlusVital(
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.vitals.weight.label"),
				Context.getService(IsantePlusPatientDashboardService.class).getLatestWeightForPatient(patient),
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.vitals.weight.unit"));
		IsantePlusVital temperature = new IsantePlusVital(
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.vitals.temperature.label"),
				Context.getService(IsantePlusPatientDashboardService.class).getLatestTemperatureForPatient(patient),
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.vitals.temperature.unit"));
		IsantePlusVital pulse = new IsantePlusVital(
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.vitals.pulse.label"),
				Context.getService(IsantePlusPatientDashboardService.class).getLatestPulseForPatient(patient),
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.vitals.pulse.unit"));
		IsantePlusVital respiratoryRate = new IsantePlusVital(
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.vitals.respiratoryRate.label"),
				Context.getService(IsantePlusPatientDashboardService.class).getLatestRespiratoryRateForPatient(patient),
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.vitals.respiratoryRate.unit"));
		IsantePlusVital bloodOxygenSaturation = new IsantePlusVital(
				Context.getMessageSourceService()
						.getMessage("isantepluspatientdashboard.vitals.bloodOxygenSaturation.label"),
				Context.getService(IsantePlusPatientDashboardService.class)
						.getLatestBloodOxygenSaturationForPatient(patient),
				Context.getMessageSourceService()
						.getMessage("isantepluspatientdashboard.vitals.bloodOxygenSaturation.unit"));
		IsantePlusVital bloodPressure = new IsantePlusVital(
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.vitals.bloodPressure.label"),
				Context.getService(IsantePlusPatientDashboardService.class).getLatestBloodPressureForPatient(patient),
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.vitals.bloodPressure.unit"));
		List<IsantePlusVital> vitals = new ArrayList<IsantePlusVital>();
		IsantePlusVital bmi = new IsantePlusVital(
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.vitals.bmi.label"),
				calculateLatestBMI(patient),
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.vitals.bmi.unit"));

		vitals.add(height);
		vitals.add(weight);
		vitals.add(bmi);
		vitals.add(temperature);
		vitals.add(pulse);
		vitals.add(respiratoryRate);
		vitals.add(bloodPressure);
		vitals.add(bloodOxygenSaturation);

		model.put("vitals", vitals);
	}
}