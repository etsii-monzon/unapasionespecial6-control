
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActivityRepository;
import domain.Activity;
import domain.Conference;
import domain.Panel;

@Service
@Transactional
public class ActivityService {

	@Autowired
	private ActivityRepository		activityRepository;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private ConferenceService		confService;


	public Activity create(final int conferenceId) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Activity res;
		Conference conf;

		conf = this.confService.findOne(conferenceId);
		res = new Panel();
		res.setConference(conf);

		return res;
	}

	public Activity findOne(final int activityId) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Activity activity;
		activity = this.activityRepository.findOne(activityId);
		return activity;
	}

	public Collection<Activity> findAll() {
		Assert.isTrue(this.adminService.checkPrincipal());
		return this.activityRepository.findAll();
	}

	public Activity save(final Activity p) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Activity res;

		res = this.activityRepository.save(p);

		return res;

	}

	public void delete(final Activity p) {
		Assert.isTrue(this.adminService.checkPrincipal());
		Assert.notNull(p);
		this.activityRepository.delete(p);

	}

	public Collection<Activity> findAllActivitiesByConference(final Conference conf) {
		final Collection<Activity> res = new ArrayList<Activity>();
		final Collection<Activity> aux = this.findAll();

		for (final Activity act : aux)
			if (act.getConference().equals(conf))
				res.add(act);

		return res;
	}
}
