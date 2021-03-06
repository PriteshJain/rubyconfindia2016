package sh.pritesh.rubyconfindia.confsched.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import sh.pritesh.rubyconfindia.confsched.R;
import sh.pritesh.rubyconfindia.confsched.activity.ActivityNavigator;
import sh.pritesh.rubyconfindia.confsched.activity.ContributorsActivity;
import sh.pritesh.rubyconfindia.confsched.databinding.FragmentAboutBinding;
import sh.pritesh.rubyconfindia.confsched.util.AppUtil;
import sh.pritesh.rubyconfindia.confsched.util.LocaleUtil;

public class AboutFragment extends BaseFragment {

    private static final String REP_TWITTER_NAME = "gautamrege";
    private static final String CONF_TWITTER_NAME = "RubyConfIndia";
    private static final String CONF_FACEBOOK_NAME = "RubyConfIndia";
    private static final String CONF_REPOSITORY_NAME = "priteshjain/rubyconfindia2016";
    private static final String LICENSE_URL = "file:///android_asset/license.html";

    @Inject
    ActivityNavigator activityNavigator;

    private FragmentAboutBinding binding;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    private void initView() {
        String repText = getString(R.string.about_rep, REP_TWITTER_NAME);
        binding.txtRep.setText(repText);
        AppUtil.linkify(getActivity(), binding.txtRep, REP_TWITTER_NAME, AppUtil.getTwitterUrl(REP_TWITTER_NAME));

        String siteUrl = getString(R.string.about_site_url);
        binding.txtSiteUrl.setText(LocaleUtil.getRtlConsideredText(siteUrl));
        AppUtil.linkify(getActivity(), binding.txtSiteUrl, siteUrl, siteUrl);

        binding.imgFacebookClicker.setOnClickListener(v ->
                AppUtil.showWebPage(getActivity(), AppUtil.getFacebookUrl(CONF_FACEBOOK_NAME)));
        binding.imgTwitterClicker.setOnClickListener(v ->
                AppUtil.showWebPage(getActivity(), AppUtil.getTwitterUrl(CONF_TWITTER_NAME)));

        binding.txtLicense.setOnClickListener(v ->
                activityNavigator.showWebView(getContext(), LICENSE_URL, getString(R.string.about_license)));

        binding.txtGithubRepository.setOnClickListener(v ->
                AppUtil.showWebPage(getActivity(), AppUtil.getGitHubUrl(CONF_REPOSITORY_NAME)));

        binding.txtContributors.setOnClickListener(v ->
                        startActivity(ContributorsActivity.createIntent(getContext()))
        );
        binding.txtYoutube.setOnClickListener(v -> {
            AppUtil.showWebPage(getActivity(), "https://www.youtube.com/channel/UCRNZy_ouJ1ai_uYwDBR2J5Q");
        });
        binding.txtVersion.setText(AppUtil.getVersionName(getContext()));
    }

}
