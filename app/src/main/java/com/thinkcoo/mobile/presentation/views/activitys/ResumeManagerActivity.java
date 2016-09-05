package com.thinkcoo.mobile.presentation.views.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.db.sqlite.DbUtils;
import com.google.gson.reflect.TypeToken;
import com.thinkcoo.mobile.AppActivityManager;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.ThinkCooApp;
import com.thinkcoo.mobile.base.bean.RequestNetBean;
import com.thinkcoo.mobile.base.response.BaseResponse;
import com.thinkcoo.mobile.base.ui.BaseActivity;
import com.thinkcoo.mobile.common.db.DB;
import com.thinkcoo.mobile.common.finals.Constant.ActionId;
import com.thinkcoo.mobile.common.finals.Constant.URL;
import com.thinkcoo.mobile.common.widget.flowlayout.FlowLayout;
import com.thinkcoo.mobile.common.widget.flowlayout.TagAdapter;
import com.thinkcoo.mobile.common.widget.flowlayout.TagFlowLayout;
import com.thinkcoo.mobile.getjob.adapter.ResumeHarvestAdapter;
import com.thinkcoo.mobile.getjob.adapter.ResumeItemBean;
import com.thinkcoo.mobile.getjob.bean.JobIntentBean;
import com.thinkcoo.mobile.personal.bean.HarvestListBean;
import com.thinkcoo.mobile.personal.bean.HarvestListBean.HarvestList;
import com.thinkcoo.mobile.personal.bean.PersonSkillBean.SkillBean;
import com.thinkcoo.mobile.personal.bean.PersonalHobbyBean.HobbyBean;
import com.thinkcoo.mobile.personal.bean.PersonalUserInfo;
import com.thinkcoo.mobile.personal.bean.StudyStateBean;
import com.thinkcoo.mobile.personal.ui.AcademicEducationActivity;
import com.thinkcoo.mobile.personal.ui.FulltimeWorkState;
import com.thinkcoo.mobile.personal.ui.HarvestActivity;
import com.thinkcoo.mobile.personal.ui.HarvestAdd;
import com.thinkcoo.mobile.personal.ui.MyBasicInformation;
import com.thinkcoo.mobile.personal.ui.MyUpdata;
import com.thinkcoo.mobile.personal.ui.ParttimeWorkState;
import com.thinkcoo.mobile.personal.ui.PersonalHobbyActivity;
import com.thinkcoo.mobile.personal.ui.PersonalSkillActivity;
import com.thinkcoo.mobile.personal.ui.StateActivity;
import com.thinkcoo.mobile.personal.ui.TrainingStudiesActivity;
import com.thinkcoo.mobile.utils.ActionSheetDialog;
import com.thinkcoo.mobile.utils.ActionSheetDialog.OnSheetItemClickListener;
import com.thinkcoo.mobile.utils.ActionSheetDialog.SheetItemColor;
import com.thinkcoo.mobile.utils.Tools.T_Intent;
import com.thinkcoo.mobile.utils.WidgetTools.WT_Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 
 * @author 简历编辑
 * @version V1.0
 * @since JDK1.7
 * @CheckItem 自己填写
 */
public class ResumeManagerActivity extends BaseActivity implements
		OnClickListener {
	private ImageView iv_titleComplete;// 添加
	private TextView tv_titleName;// title
	private ImageView img_back;
	private TextView image_resume_manager_get_job_gola_shrink_textView2,
			txt_resume_manager_expect_salary,
			txt_resume_manager_industry_direction,
			txt_resume_manager_work_place, txt_resume_manager_job_type,
			txt_resume_manager_position_name,
			rl_resume_manager_selfinfo_textView2,
			txt_resume_manager_info_certype, txt_resume_manager_info_certnum,
			txt_resume_manager_info_birthday,
			txt_resume_manager_info_politics_status,
			txt_resume_manager_info_marital_status,
			txt_resume_manager_info_nation,
			txt_resume_manager_info_education_bg,
			txt_resume_manager_info_residence,
			txt_resume_manager_info_birth_place,
			rl_resume_manager_study_state_textView2,
			rl_resume_manager_work_state_textView2,
			rl_resume_manager_harvest_honor_textView2, textView1,
			rl_resume_manager_selfinfo_textView1,
			rl_resume_manager_study_state_textView1,
			rl_resume_manager_work_state_textView1,
			rl_resume_manager_harvest_honor_textView1,
			txt_resume_manager_username, ac_state_study_school_sex,
			rl_resume_manager_harvest_honor_view,
			rl_resume_manager_harvest_honor_vieww,
			rl_resume_manager_harvest_honor_View2,
			rl_resume_manager_harvest_honor_Vieww2;
	private TextView txt_resume_manager_info_phone,
			txt_resume_manager_info_mail;// 手机/邮箱
	private ImageView image_resume_manager_get_job_gola_shrink,
			image_resume_manager_selfinfo_shrink,
			image_resume_manager_selfinfo_shrink_edit,
			rl_resume_manager_study_state_shrink,
			rl_resume_manager_study_state_shrink_edit,
			rl_resume_manager_work_state_shrink,
			rl_resume_manager_work_state_shrink_edit,
			rl_resume_manager_harvest_honor_shrink,
			rl_resume_manager_harvest_honor_shrink_edit,
			image_resume_manager_get_job_goal_shrink,
			rl_resume_manager_harvest_honor_shrin,
			rl_resume_manager_harvest_honor_shriin;

	private LinearLayout rl_resume_manager_get_job_goal_selfinfo,
			rl_resume_manager_study_state_info,
			rl_resume_manager_harvest_honor_info,
			rl_resume_manager_work_state_info,
			rl_resume_manager_get_job_goal_edit,
			rl_resume_manager_harvest_honor_info_skill,
			rl_resume_manager_harvest_honor_info_love;
	// 跳到自信修改个人信息是否刷新
	public static boolean isFresh;

	private RelativeLayout rl_resume_manager_sex, rl_resume_manager_username;
	private PersonalUserInfo personalUserInfo;// 基本资料信息
	private View studyView, workView, havestView, skillView, loveView;
	private LayoutInflater inflater;
	private DB db;
	private DbUtils dbUtils;
	private JobIntentBean jobBean;// 求职意向
	private List<SkillBean> mlist = null;
	private List<String> mVals = null;
	private List<HobbyBean> mlistt = null;
	private List<String> mValss = null;
	private TagFlowLayout mFlowLayout, nFlowLayout;
	private TagAdapter tagAdapter = null;
	private TagAdapter love_tagAdapter = null;
	private int mCornerSize = 6;// 圆角
	private SharedPreferences sp;// 存储文件
	private Editor editor;
	private String bircity, city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_resume_manager);
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (isFresh) {// 从自信修改完个人基本信息后刷新简历个人信息处UI
			getSelfInfoDetails();
		}
		getStudyStateList();
		getWorkStateList();
		httpgetpersonalskill();
		httpgetpersonalhobby();
		httpharvestList();
	}

	/**
	 * Description: 自己填写
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	public void initView() {
		sp = getSharedPreferences("setting", 0);
		editor = sp.edit();
		mVals = new ArrayList<String>();
		mlist = new ArrayList<SkillBean>();
		mlistt = new ArrayList<HobbyBean>();
		mValss = new ArrayList<String>();
		jobBean = (JobIntentBean) this.getIntent().getSerializableExtra("data");
		personalUserInfo = (PersonalUserInfo) this.getIntent()
				.getSerializableExtra("selfInfo");
		inflater = LayoutInflater.from(ResumeManagerActivity.this);//

		/* 个人信息 */
		txt_resume_manager_username = (TextView) findViewById(R.id.txt_resume_manager_username);
		ac_state_study_school_sex = (TextView) findViewById(R.id.ac_state_study_school_sex);

		rl_resume_manager_sex = (RelativeLayout) findViewById(R.id.rl_resume_manager_sex);
		rl_resume_manager_username = (RelativeLayout) findViewById(R.id.rl_resume_manager_username);
		rl_resume_manager_sex.setOnClickListener(this);
		rl_resume_manager_username.setOnClickListener(this);
		// rl_resume_manager_sex.setOnClickListener(this);
		/* 求职意向 */
		image_resume_manager_get_job_goal_shrink = (ImageView) findViewById(R.id.image_resume_manager_get_job_goal_shrink);
		image_resume_manager_get_job_gola_shrink_textView2 = (TextView) findViewById(R.id.image_resume_manager_get_job_gola_shrink_textView2);
		// image_resume_manager_get_job_gola_shrink = (ImageView)
		// findViewById(R.id.image_resume_manager_get_job_gola_shrink);
		txt_resume_manager_position_name = (TextView) findViewById(R.id.txt_resume_manager_position_name);
		txt_resume_manager_job_type = (TextView) findViewById(R.id.txt_resume_manager_job_type);
		txt_resume_manager_work_place = (TextView) findViewById(R.id.txt_resume_manager_work_place);
		txt_resume_manager_industry_direction = (TextView) findViewById(R.id.txt_resume_manager_industry_direction);
		txt_resume_manager_expect_salary = (TextView) findViewById(R.id.txt_resume_manager_expect_salary);
		rl_resume_manager_get_job_goal_edit = (LinearLayout) findViewById(R.id.rl_resume_manager_get_job_goal_edit);
		textView1 = (TextView) findViewById(R.id.textView1);
		image_resume_manager_get_job_goal_shrink.setOnClickListener(this);
		// image_resume_manager_get_job_gola_shrink.setOnClickListener(this);
		image_resume_manager_get_job_gola_shrink_textView2
				.setOnClickListener(this);
		textView1.setOnClickListener(this);
		/* 基本资料 */
		rl_resume_manager_selfinfo_textView2 = (TextView) findViewById(R.id.rl_resume_manager_selfinfo_textView2);
		image_resume_manager_selfinfo_shrink = (ImageView) findViewById(R.id.image_resume_manager_selfinfo_shrink);
		image_resume_manager_selfinfo_shrink_edit = (ImageView) findViewById(R.id.image_resume_manager_selfinfo_shrink_edit);
		rl_resume_manager_selfinfo_textView1 = (TextView) findViewById(R.id.rl_resume_manager_selfinfo_textView1);
		txt_resume_manager_info_certype = (TextView) findViewById(R.id.txt_resume_manager_info_certype);//证件类型
		txt_resume_manager_info_certnum = (TextView) findViewById(R.id.txt_resume_manager_info_certnum);
		txt_resume_manager_info_birthday = (TextView) findViewById(R.id.txt_resume_manager_info_birthday);
		txt_resume_manager_info_politics_status = (TextView) findViewById(R.id.txt_resume_manager_info_politics_status);
		txt_resume_manager_info_marital_status = (TextView) findViewById(R.id.txt_resume_manager_info_marital_status);
		txt_resume_manager_info_nation = (TextView) findViewById(R.id.txt_resume_manager_info_nation);
		txt_resume_manager_info_education_bg = (TextView) findViewById(R.id.txt_resume_manager_info_education_bg);
		txt_resume_manager_info_residence = (TextView) findViewById(R.id.txt_resume_manager_info_residence);
		txt_resume_manager_info_birth_place = (TextView) findViewById(R.id.txt_resume_manager_info_birth_place);

		txt_resume_manager_info_phone = (TextView) findViewById(R.id.txt_resume_manager_info_phone);// 电话
		txt_resume_manager_info_mail = (TextView) findViewById(R.id.txt_resume_manager_info_mail);// 邮箱

		rl_resume_manager_get_job_goal_selfinfo = (LinearLayout) findViewById(R.id.rl_resume_manager_get_job_goal_selfinfo);

		rl_resume_manager_selfinfo_textView2.setOnClickListener(this);
		image_resume_manager_selfinfo_shrink.setOnClickListener(this);
		image_resume_manager_selfinfo_shrink_edit.setOnClickListener(this);
		rl_resume_manager_selfinfo_textView1.setOnClickListener(this);
		/* 学习状态 */

		rl_resume_manager_study_state_shrink = (ImageView) findViewById(R.id.rl_resume_manager_study_state_shrink);
		rl_resume_manager_study_state_shrink_edit = (ImageView) findViewById(R.id.rl_resume_manager_study_state_shrink_edit);
		rl_resume_manager_study_state_textView2 = (TextView) findViewById(R.id.rl_resume_manager_study_state_textView2);
		rl_resume_manager_study_state_textView1 = (TextView) findViewById(R.id.rl_resume_manager_study_state_textView1);
		rl_resume_manager_study_state_info = (LinearLayout) findViewById(R.id.rl_resume_manager_study_state_info);

		rl_resume_manager_study_state_shrink.setOnClickListener(this);
		rl_resume_manager_study_state_shrink_edit.setOnClickListener(this);
		rl_resume_manager_study_state_textView2.setOnClickListener(this);
		rl_resume_manager_study_state_textView1.setOnClickListener(this);
		/* 工作状态 */

		rl_resume_manager_work_state_shrink = (ImageView) findViewById(R.id.rl_resume_manager_work_state_shrink);
		rl_resume_manager_work_state_shrink_edit = (ImageView) findViewById(R.id.rl_resume_manager_work_state_shrink_edit);
		rl_resume_manager_work_state_textView2 = (TextView) findViewById(R.id.rl_resume_manager_work_state_textView2);

		rl_resume_manager_work_state_info = (LinearLayout) findViewById(R.id.rl_resume_manager_work_state_info);
		rl_resume_manager_work_state_textView1 = (TextView) findViewById(R.id.rl_resume_manager_work_state_textView1);
		rl_resume_manager_work_state_shrink.setOnClickListener(this);
		rl_resume_manager_work_state_shrink_edit.setOnClickListener(this);
		rl_resume_manager_work_state_textView2.setOnClickListener(this);
		rl_resume_manager_work_state_textView1.setOnClickListener(this);

		/* 收获与荣誉 */
		rl_resume_manager_harvest_honor_shrink = (ImageView) findViewById(R.id.rl_resume_manager_harvest_honor_shrink);// 向下箭头
		rl_resume_manager_harvest_honor_shrink_edit = (ImageView) findViewById(R.id.rl_resume_manager_harvest_honor_shrink_edit);// 向左箭头
		rl_resume_manager_harvest_honor_textView2 = (TextView) findViewById(R.id.rl_resume_manager_harvest_honor_textView2);// 去自信去

		rl_resume_manager_harvest_honor_info = (LinearLayout) findViewById(R.id.rl_resume_manager_harvest_honor_info);// 下面的空位
		rl_resume_manager_harvest_honor_textView1 = (TextView) findViewById(R.id.rl_resume_manager_harvest_honor_textView1);// 标题
		rl_resume_manager_harvest_honor_shrink.setOnClickListener(this);
		rl_resume_manager_harvest_honor_shrink_edit.setOnClickListener(this);
		rl_resume_manager_harvest_honor_textView2.setOnClickListener(this);
		rl_resume_manager_harvest_honor_textView1.setOnClickListener(this);

		/* 我的技能 */
		rl_resume_manager_harvest_honor_view = (TextView) findViewById(R.id.rl_resume_manager_harvest_honor_view);// 标题
		rl_resume_manager_harvest_honor_View2 = (TextView) findViewById(R.id.rl_resume_manager_harvest_honor_View2);// 去自信完善
		rl_resume_manager_harvest_honor_shrin = (ImageView) findViewById(R.id.rl_resume_manager_harvest_honor_shrin);// 向下箭头
		rl_resume_manager_harvest_honor_info_skill = (LinearLayout) findViewById(R.id.rl_resume_manager_harvest_honor_info_skill);// 下面的空位
		rl_resume_manager_harvest_honor_view.setOnClickListener(this);
		rl_resume_manager_harvest_honor_shrin.setOnClickListener(this);
		rl_resume_manager_harvest_honor_View2.setOnClickListener(this);
		/* 我的爱好 */
		rl_resume_manager_harvest_honor_vieww = (TextView) findViewById(R.id.rl_resume_manager_harvest_honor_vieww);// 标题
		rl_resume_manager_harvest_honor_Vieww2 = (TextView) findViewById(R.id.rl_resume_manager_harvest_honor_Vieww2);// 去自信完善
		rl_resume_manager_harvest_honor_shriin = (ImageView) findViewById(R.id.rl_resume_manager_harvest_honor_shriin);// 向下箭头
		rl_resume_manager_harvest_honor_info_love = (LinearLayout) findViewById(R.id.rl_resume_manager_harvest_honor_info_love);// 下面的空位
		rl_resume_manager_harvest_honor_vieww.setOnClickListener(this);
		rl_resume_manager_harvest_honor_shriin.setOnClickListener(this);
		rl_resume_manager_harvest_honor_Vieww2.setOnClickListener(this);
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);

		iv_titleComplete = (ImageView) findViewById(R.id.iv_titleComplete);// 添加
		tv_titleName = (TextView) findViewById(R.id.tv_titleName);// title
		tv_titleName.setText(R.string.getjob_resumepreview_title);
		iv_titleComplete.setImageResource(R.drawable.self_resume_refresh);
		iv_titleComplete.setVisibility(View.VISIBLE);
		iv_titleComplete.setOnClickListener(this);// 添加收获点击事件
		loveView = inflater.inflate(R.layout.resume_love, null, false);// 爱好
		skillView = inflater.inflate(R.layout.resume_skill, null, false);// 技能
		havestView = inflater.inflate(R.layout.ac_resume_listview, null, false);// 收获
		studyView = inflater.inflate(R.layout.ac_resume_listview, null, false);// 学习
		workView = inflater.inflate(R.layout.ac_resume_listview, null, false);// 工作
		if (jobBean != null) {// 传过来的数据不为空则更新UI
			initJobIntent(jobBean);
		}
		if (personalUserInfo != null) {// 传过来的个人信息是否为空
			initSelfInfoView();
		}

	}

	public void initJobIntent(JobIntentBean jobBean) {
		txt_resume_manager_position_name.setText(jobBean.getJobDisplay());
		txt_resume_manager_job_type.setText(jobBean.getJobTypeDisplay());
		txt_resume_manager_work_place.setText(jobBean.getPlaceyDisplay());////????
		txt_resume_manager_industry_direction.setText(jobBean
				.getIndustryDisplay());
		txt_resume_manager_expect_salary.setText(jobBean.getMoneyDisplay());

	}

	public void initSelfInfoView() {
		txt_resume_manager_username.setText(personalUserInfo.getFullName());// ////
		if ("null".equals(personalUserInfo.getSex())) {
			ac_state_study_school_sex.setText("");// //////
		} else if (!"null".equals(personalUserInfo.getSex())) {
			ac_state_study_school_sex.setText(personalUserInfo.getSex());// //////
		}
		txt_resume_manager_info_certype
				.setText(getCertificateName(personalUserInfo
						.getCertificateType()));//证件类型
		txt_resume_manager_info_certnum.setText(personalUserInfo
				.getCertificateNumber());
		txt_resume_manager_info_birthday.setText(personalUserInfo
				.getBirthDate());

		txt_resume_manager_info_phone.setText(personalUserInfo
				.getPersonalPhone());
		txt_resume_manager_info_mail.setText(personalUserInfo.getMail());
		txt_resume_manager_info_politics_status.setText(personalUserInfo
				.getPoliticalStatus());
		txt_resume_manager_info_marital_status.setText(personalUserInfo
				.getMaritalStatus());
		txt_resume_manager_info_nation.setText(personalUserInfo.getNation());
		txt_resume_manager_info_education_bg.setText(personalUserInfo
				.getHighestEducation());
		if (personalUserInfo.getLiveAreaCode() != null
				&& personalUserInfo.getLiveAreaCode().length() > 0) {
			if (personalUserInfo.getLiveAreaCode().contains("市辖区")) {
				city = personalUserInfo.getLiveAreaCode().replace("市辖区", "");
			} else {
				// 居住地
				city = personalUserInfo.getLiveAreaCode();// 省市区
			}
		}

		if (personalUserInfo.getLiveStreet() != null&&personalUserInfo.getLiveStreet().length()>0) {// 对老版本没有此字段做兼容
			txt_resume_manager_info_residence.setText(city + ""
					+ personalUserInfo.getLiveStreet());// 居住地
		} else {
			txt_resume_manager_info_residence.setText(city);// 居住地
		}
		if (personalUserInfo.getBirthPlace() != null
				&& personalUserInfo.getBirthPlace().length() > 0) {
			if (personalUserInfo.getBirthPlace().contains("市辖区")) {
				bircity = personalUserInfo.getBirthPlace().replace("市辖区", "");
			} else {
				// 居住地
				bircity = personalUserInfo.getBirthPlace();// 省市区
			}
		}

		if (personalUserInfo.getBirthStreet() != null&&personalUserInfo.getBirthStreet().length()>0) {
			txt_resume_manager_info_birth_place.setText(bircity + ""
					+ personalUserInfo.getBirthStreet());// 出生地
		} else {
			txt_resume_manager_info_birth_place.setText(bircity);// 出生地

		}
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		// 显示动画
		TranslateAnimation mShowAction = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f);
		mShowAction.setDuration(500);

		// 隐藏动画
		TranslateAnimation mHiddenAction = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f);
		mHiddenAction.setDuration(500);
		switch (view.getId()) {
		case R.id.iv_titleComplete:// //刷新简历点击
			refreshResume();
			getSelfInfoDetails();
			break;

		case R.id.rl_resume_manager_username:// 姓名
			PersonalUserInfo info = ThinkCooApp.getInstance()
					.getPersonalUserInfo();
			String strOne = txt_resume_manager_username.getText().toString();// 姓名
			String strTwo = info.getIdType();// 身份类型
			String strThree = ac_state_study_school_sex.getText().toString();// 性别

			intent.setClass(ResumeManagerActivity.this, MyUpdata.class);
			intent.putExtra("code", MyUpdata.UPDATA_NAME);
			intent.putExtra("context", strOne + ",--" + strTwo + ",--"
					+ strThree);
			startActivityForResult(intent, 3);
			break;

		case R.id.rl_resume_manager_sex:// 性别
			new ActionSheetDialog(ResumeManagerActivity.this)
					.builder()
					.setCancelable(false)
					.setCanceledOnTouchOutside(false)
					.addSheetItem("男", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									ac_state_study_school_sex.setText("男");
									httpUpdataName();
								}
							})
					.addSheetItem("女", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									ac_state_study_school_sex.setText("女");
									httpUpdataName();
								}
							}).show();
			break;

		case R.id.image_resume_manager_get_job_goal_shrink:
			if (rl_resume_manager_get_job_goal_edit.getVisibility() == View.GONE) {

				rl_resume_manager_get_job_goal_edit.setVisibility(View.VISIBLE);
				rl_resume_manager_get_job_goal_edit.startAnimation(mShowAction);
			} else {
				rl_resume_manager_get_job_goal_edit.setVisibility(View.GONE);
				rl_resume_manager_get_job_goal_edit
						.startAnimation(mHiddenAction);
			}
			break;

		case R.id.textView1:
			if (rl_resume_manager_get_job_goal_edit.getVisibility() == View.GONE) {

				rl_resume_manager_get_job_goal_edit.setVisibility(View.VISIBLE);
				rl_resume_manager_get_job_goal_edit.startAnimation(mShowAction);
			} else {
				rl_resume_manager_get_job_goal_edit.setVisibility(View.GONE);
				rl_resume_manager_get_job_goal_edit
						.startAnimation(mHiddenAction);
			}
			break;
		case R.id.image_resume_manager_get_job_gola_shrink_textView2:// 求职意向
			intent.setClass(ResumeManagerActivity.this,
					GetJobIntentActivity.class);
			startActivityForResult(intent, 1);
			break;

		case R.id.rl_resume_manager_selfinfo_textView2:// 基本资料
			bundle.putString("fromflag", "ResumeManagerActivity");
			T_Intent.startActivity(ResumeManagerActivity.this,
					MyBasicInformation.class, bundle);
			break;

		case R.id.rl_resume_manager_selfinfo_textView1:
			if (rl_resume_manager_get_job_goal_selfinfo.getVisibility() == View.GONE) {
				rl_resume_manager_get_job_goal_selfinfo
						.startAnimation(mShowAction);
				rl_resume_manager_get_job_goal_selfinfo
						.setVisibility(View.VISIBLE);
			} else {
				rl_resume_manager_get_job_goal_selfinfo
						.startAnimation(mHiddenAction);
				rl_resume_manager_get_job_goal_selfinfo
						.setVisibility(View.GONE);
			}
			break;

		case R.id.image_resume_manager_selfinfo_shrink:
			if (rl_resume_manager_get_job_goal_selfinfo.getVisibility() == View.GONE) {
				rl_resume_manager_get_job_goal_selfinfo
						.startAnimation(mShowAction);
				rl_resume_manager_get_job_goal_selfinfo
						.setVisibility(View.VISIBLE);
			} else {
				rl_resume_manager_get_job_goal_selfinfo
						.startAnimation(mHiddenAction);
				rl_resume_manager_get_job_goal_selfinfo
						.setVisibility(View.GONE);
			}
			break;

		case R.id.image_resume_manager_selfinfo_shrink_edit:// 基本资料
			bundle.putString("fromflag", "ResumeManagerActivity");
			T_Intent.startActivity(ResumeManagerActivity.this,
					MyBasicInformation.class, bundle);
			break;

		case R.id.rl_resume_manager_study_state_textView1:
			if (rl_resume_manager_study_state_info.getVisibility() == View.GONE)
				rl_resume_manager_study_state_info.setVisibility(View.VISIBLE);
			else
				rl_resume_manager_study_state_info.setVisibility(View.GONE);
			break;

		case R.id.rl_resume_manager_study_state_shrink:
			if (rl_resume_manager_study_state_info.getVisibility() == View.GONE)
				rl_resume_manager_study_state_info.setVisibility(View.VISIBLE);
			else
				rl_resume_manager_study_state_info.setVisibility(View.GONE);
			break;

		case R.id.rl_resume_manager_study_state_shrink_edit:// 教育经历
			T_Intent.startActivity(ResumeManagerActivity.this,
					StateActivity.class, bundle);
			break;
		case R.id.rl_resume_manager_study_state_textView2:// 教育经历
			T_Intent.startActivity(ResumeManagerActivity.this,
					StateActivity.class, bundle);
			break;

		case R.id.rl_resume_manager_work_state_textView1:
			if (rl_resume_manager_work_state_info.getVisibility() == View.GONE)
				rl_resume_manager_work_state_info.setVisibility(View.VISIBLE);
			else
				rl_resume_manager_work_state_info.setVisibility(View.GONE);
			break;

		case R.id.rl_resume_manager_work_state_shrink:
			if (rl_resume_manager_work_state_info.getVisibility() == View.GONE)
				rl_resume_manager_work_state_info.setVisibility(View.VISIBLE);
			else
				rl_resume_manager_work_state_info.setVisibility(View.GONE);
			break;

		case R.id.rl_resume_manager_work_state_shrink_edit:// 工作经历
			T_Intent.startActivity(ResumeManagerActivity.this,
					StateActivity.class, bundle);
			break;

		case R.id.rl_resume_manager_work_state_textView2:// 工作经历
			T_Intent.startActivity(ResumeManagerActivity.this,
					StateActivity.class, bundle);
			break;

		case R.id.rl_resume_manager_harvest_honor_textView1:// 收获标题
			if (rl_resume_manager_harvest_honor_info.getVisibility() == View.GONE)
				rl_resume_manager_harvest_honor_info
						.setVisibility(View.VISIBLE);
			else
				rl_resume_manager_harvest_honor_info.setVisibility(View.GONE);
			break;

		case R.id.rl_resume_manager_harvest_honor_shrink:// 收获向下箭头
			if (rl_resume_manager_harvest_honor_info.getVisibility() == View.GONE)
				rl_resume_manager_harvest_honor_info
						.setVisibility(View.VISIBLE);
			else
				rl_resume_manager_harvest_honor_info.setVisibility(View.GONE);

			break;

		case R.id.rl_resume_manager_harvest_honor_shrink_edit:
			ThinkCooApp.getInstance().strHarvest = "1";
			Bundle bund = new Bundle();
			T_Intent.startActivity(ResumeManagerActivity.this,
					HarvestActivity.class, bund);
			break;
		case R.id.rl_resume_manager_harvest_honor_textView2:
			ThinkCooApp.getInstance().strHarvest = "1";
			Bundle bundl = new Bundle();
			T_Intent.startActivity(ResumeManagerActivity.this,
					HarvestActivity.class, bundl);
			break;

		case R.id.rl_resume_manager_harvest_honor_view:// 技能标题
			if (rl_resume_manager_harvest_honor_info_skill.getVisibility() == View.GONE)
				rl_resume_manager_harvest_honor_info_skill
						.setVisibility(View.VISIBLE);
			else
				rl_resume_manager_harvest_honor_info_skill
						.setVisibility(View.GONE);
			break;

		case R.id.rl_resume_manager_harvest_honor_shrin:// 技能向下箭头
			if (rl_resume_manager_harvest_honor_info_skill.getVisibility() == View.GONE)
				rl_resume_manager_harvest_honor_info_skill
						.setVisibility(View.VISIBLE);
			else
				rl_resume_manager_harvest_honor_info_skill
						.setVisibility(View.GONE);

			break;
		case R.id.rl_resume_manager_harvest_honor_View2:// 技能点击
			T_Intent.startActivity(ResumeManagerActivity.this,
					PersonalSkillActivity.class, bundle);
			break;
		case R.id.rl_resume_manager_harvest_honor_vieww:// 爱好标题
			if (rl_resume_manager_harvest_honor_info_love.getVisibility() == View.GONE)
				rl_resume_manager_harvest_honor_info_love
						.setVisibility(View.VISIBLE);
			else
				rl_resume_manager_harvest_honor_info_love
						.setVisibility(View.GONE);
			break;

		case R.id.rl_resume_manager_harvest_honor_shriin:// 爱好向下箭头
			if (rl_resume_manager_harvest_honor_info_love.getVisibility() == View.GONE)
				rl_resume_manager_harvest_honor_info_love
						.setVisibility(View.VISIBLE);
			else
				rl_resume_manager_harvest_honor_info_love
						.setVisibility(View.GONE);
			break;
		case R.id.rl_resume_manager_harvest_honor_Vieww2:// 爱好点击
			T_Intent.startActivity(ResumeManagerActivity.this,
					PersonalHobbyActivity.class, bundle);
			break;
		case R.id.img_back:

			AppActivityManager.getAppActivityManager().finishActivity();
			break;
		}
	}

	/**
	 * Description: 自己填写
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @see android.app.Activity#onActivityResult(int, int,
	 *      Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == 3) {
			jobBean = (JobIntentBean) data.getSerializableExtra("data");
			initJobIntent(jobBean);
		}

		if (requestCode == 3 && resultCode == 3) {
			data.getStringExtra("data");
			txt_resume_manager_username.setText(data.getStringExtra("data"));
		}
	}

	public class itemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Bundle bundle = new Bundle();
			bundle.putString("ActivityFlag", "HarvestActivity");
			T_Intent.startActivity(ResumeManagerActivity.this,
					ResumeManagerActivity.class, bundle);
		}

	}

	/**
	 * 获取个人基于信息 Description: 自己填写
	 */
	public void getSelfInfoDetails() {
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("userId", ThinkCooApp.mUserBean.getUserId());
		String url = ThinkCooApp.getInstance().webNodes
				.get(URL.PERSONAL_PREFIX) + "/querypersonalinformation.json";
		TypeToken<BaseResponse<PersonalUserInfo>> typeToken = new TypeToken<BaseResponse<PersonalUserInfo>>() {
		};
		RequestNetBean<?, ?> requestNet = new RequestNetBean<BaseResponse<PersonalUserInfo>, ResumeManagerActivity>(
				ResumeManagerActivity.this, url, params, ActionId.PERSONA_INFO,
				false, 1, typeToken, ResumeManagerActivity.class);
		ThinkCooApp.getInstance().get(requestNet);
	}

	/**
	 * 获取个人求职意向 Description: 自己填写
	 */
	public void getSelfJobIntent() {
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("userId", ThinkCooApp.mUserBean.getUserId());
		String url = ThinkCooApp.getInstance().webNodes.get(URL.GETJOB_PREFIX)
				+ "/queryjobintention.json";
		TypeToken<BaseResponse<JobIntentBean>> typeToken = new TypeToken<BaseResponse<JobIntentBean>>() {
		};
		RequestNetBean<?, ?> requestNet = new RequestNetBean<BaseResponse<JobIntentBean>, ResumeManagerActivity>(
				ResumeManagerActivity.this, url, params,
				ActionId.SELECT_JOB_INTENT, false, 1, typeToken,
				ResumeManagerActivity.class);
		ThinkCooApp.getInstance().get(requestNet);
	}

	/**
	 * 查询学习状态 Description: 自己填写
	 */
	public void getStudyStateList() {
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("userId", ThinkCooApp.mUserBean.getUserId());// 用户ID
		String url = ThinkCooApp.getInstance().webNodes
				.get(URL.PERSONAL_PREFIX) + "/querystudyvitae.json";
		TypeToken<BaseResponse<List<StudyStateBean>>> typeToken = new TypeToken<BaseResponse<List<StudyStateBean>>>() {
		};
		RequestNetBean<?, ?> requestNet = new RequestNetBean<BaseResponse<List<StudyStateBean>>, ResumeManagerActivity>(
				ResumeManagerActivity.this, url, params,
				ActionId.SELECT_STUDY_STATE, false, 1, typeToken,
				ResumeManagerActivity.class);
		ThinkCooApp.getInstance().get(requestNet);
	}

	/**
	 * 查询工作状态 Description: 自己填写
	 */
	public void getWorkStateList() {
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("userId", ThinkCooApp.mUserBean.getUserId());// 用户ID
		String url = ThinkCooApp.getInstance().webNodes
				.get(URL.PERSONAL_PREFIX) + "/queryworkvitae.json";
		TypeToken<BaseResponse<List<StudyStateBean>>> typeToken = new TypeToken<BaseResponse<List<StudyStateBean>>>() {
		};
		RequestNetBean<?, ?> requestNet = new RequestNetBean<BaseResponse<List<StudyStateBean>>, ResumeManagerActivity>(
				ResumeManagerActivity.this, url, params,
				ActionId.SELECT_WORK_STATE, false, 1, typeToken,
				ResumeManagerActivity.class);
		ThinkCooApp.getInstance().get(requestNet);
	}

	/**
	 * Description: 获得个人技能列表的网络请求
	 */
	private void httpgetpersonalskill() {
		// TODO Auto-generated method stub
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("userId", ThinkCooApp.mUserBean.getUserId());
		String url = ThinkCooApp.getInstance().webNodes
				.get(URL.PERSONAL_PREFIX) + "queryskill.json";

		TypeToken<BaseResponse<List<SkillBean>>> typeToken = new TypeToken<BaseResponse<List<SkillBean>>>() {
		};
		RequestNetBean<?, ?> requestNet = new RequestNetBean<BaseResponse<List<SkillBean>>, ResumeManagerActivity>(
				ResumeManagerActivity.this, url, params,
				ActionId.PERSONAL_SELECT_SKILL, false, 1, typeToken,
				ResumeManagerActivity.class);
		ThinkCooApp.getInstance().get(requestNet);
	}

	/**
	 * Description: 获得个人爱好列表的网络请求
	 */
	private void httpgetpersonalhobby() {
		// TODO Auto-generated method stub
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("userId", ThinkCooApp.mUserBean.getUserId());
		String url = ThinkCooApp.getInstance().webNodes
				.get(URL.PERSONAL_PREFIX) + "queryhobby.json";

		TypeToken<BaseResponse<List<HobbyBean>>> typeToken = new TypeToken<BaseResponse<List<HobbyBean>>>() {
		};
		RequestNetBean<?, ?> requestNet = new RequestNetBean<BaseResponse<List<HobbyBean>>, ResumeManagerActivity>(
				ResumeManagerActivity.this, url, params,
				ActionId.PERSONAL_SELECT_HOBBY, false, 1, typeToken,
				ResumeManagerActivity.class);
		ThinkCooApp.getInstance().get(requestNet);
	}

	/**
	 * Description: 获取我的收获列表
	 */
	public void httpharvestList() {
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("userId", ThinkCooApp.mUserBean.getUserId());
		params.put("pageIndex", "1");
		params.put("pageSize", "100");
		String url = ThinkCooApp.getInstance().webNodes
				.get(URL.PERSONAL_PREFIX) + "/querygrant.json";
		TypeToken<BaseResponse<HarvestListBean>> typeToken = new TypeToken<BaseResponse<HarvestListBean>>() {
		};
		RequestNetBean<?, ?> requestNet = new RequestNetBean<BaseResponse<HarvestListBean>, ResumeManagerActivity>(
				ResumeManagerActivity.this, url, params, ActionId.HARVEST_LIST,
				true, 1, typeToken, ResumeManagerActivity.class);
		ThinkCooApp.getInstance().get(requestNet);
	}

	@Override
	public void callBackSwitch(BaseResponse<?> response) {
		int key = response.getKey();
		int status = response.getStatus();
		switch (key) {
		case ActionId.PERSONAL_UPDATA_FULLNAME:
			if (1 == status) {// 修改姓名
				PersonalUserInfo info = ThinkCooApp.getInstance()
						.getPersonalUserInfo();
				info.setSex(ac_state_study_school_sex.getText().toString());
				ThinkCooApp.getInstance().setPersonalUserInfo(info);
			} else {
			}
			break;

		case ActionId.PERSONA_INFO:// 获取信息
			if (0 == status) {// 业务级的异常---用户提示如用户密码错误
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (1 == status) {// 查询成功，有值，没值
				// 正常处理
				if (response.getData() != null) {
					personalUserInfo = (PersonalUserInfo) response.getData();

					try {
						txt_resume_manager_info_residence
								.setText(personalUserInfo.getLiveAreaCode()
										.replace(" ", ""));
						txt_resume_manager_info_birth_place
								.setText(personalUserInfo.getBirthPlace()
										.replace(" ", ""));
					} catch (Exception e) {
						e.printStackTrace();
					}
					initSelfInfoView();
				}
			} else if (-1 == status) {// 调试处理，如请求参数不对
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (-2 == status) {// 服务器异常，用于调试
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			}
			break;

		case ActionId.HARVEST_LIST:// “我的收获”列表
			if (0 == status) {// 业务级的异常---用户提示如用户密码错误
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (status == 1) {
				if (response.getData() == null)
					return;
				HarvestListBean harvestListBean = (HarvestListBean) response
						.getData();// 获取数据
				ArrayList<HarvestList> harvestList = (ArrayList<HarvestList>) harvestListBean
						.getList();
				ListView listView = (ListView) havestView
						.findViewById(R.id.item_resume_listview_body);// 实例listview
				final ArrayList<ResumeItemBean> list = new ArrayList<ResumeItemBean>();// 实例化集合
				for (int i = 0; harvestList != null && i < harvestList.size(); i++) {
					ResumeItemBean bean = new ResumeItemBean();
					bean.setTime(harvestList.get(i).getGrantTime());
					bean.setName(harvestList.get(i).getGrantName());
					bean.setVitaeId(harvestList.get(i).getGrantId());
					list.add(bean);
				}
				ResumeHarvestAdapter adapter = new ResumeHarvestAdapter(list,
						ResumeManagerActivity.this);// 实例化适配器
				listView.setAdapter(adapter);
				setGridViewHeightBasedOnChildren(listView);
				rl_resume_manager_harvest_honor_info.removeAllViews();// 清除
				rl_resume_manager_harvest_honor_info.addView(havestView);// 加入含有listview的界面
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Bundle bundle = new Bundle();
						bundle.putString("harvestId", list.get(position)
								.getVitaeId());
						bundle.putString("flag", "3");
						T_Intent.startActivity(ResumeManagerActivity.this,
								HarvestAdd.class, bundle);
					}
				});

			} else if (-1 == status) {// 调试处理，如请求参数不对
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (-2 == status) {// 服务器异常，用于调试
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			}

			break;
		case ActionId.PERSONAL_SELECT_SKILL:// “我的技能”列表
			if (0 == status) {//
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (status == 1) {
				if (response.getData() == null)
					return;
				mlist = (List<SkillBean>) response.getData();
				mVals.clear();
				if (null != mlist && mlist.size() > 0) {

					for (int i = 0; i < mlist.size(); i++) {
						mVals.add(mlist.get(i).getSkill());
					}

				}

				mFlowLayout = (TagFlowLayout) skillView
						.findViewById(R.id.getjob_resume_skill);

				tagAdapter = new TagAdapter<String>(mVals) {
					final LayoutInflater mInflater = LayoutInflater
							.from(ResumeManagerActivity.this);

					@SuppressLint("NewApi")
					@Override
					public View getView(FlowLayout parent, final int position,
							final String s) {
						final View rl = mInflater.inflate(
								R.layout.item_person_list, mFlowLayout, false);
						final TextView tv = (TextView) rl
								.findViewById(R.id.text_item_personal_skill);
						try {
							if (!"".equals(s)) {
								if (s.contains("#")) {

									// 获取最后"#"的位置
									int lastIndex = s.lastIndexOf("#");
									// 获取爱好内容
									tv.setText(s.subSequence(0, lastIndex));
									// 获取背景颜色值
									String blockColor = s.substring(lastIndex,
											s.length());
									int spec = MeasureSpec.makeMeasureSpec(0,
											MeasureSpec.UNSPECIFIED);
									tv.measure(spec, spec);
									setBackgroundRounded(tv.getMeasuredWidth(),
											tv.getMeasuredHeight(), tv,
											Color.parseColor(blockColor));
								} else {
									tv.setText(s);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						tv.setPadding(30, 4, 30, 4);
						return rl;
					}
				};
				mFlowLayout.setAdapter(tagAdapter);
				rl_resume_manager_harvest_honor_info_skill.removeAllViews();// 清除
				rl_resume_manager_harvest_honor_info_skill.addView(skillView);// 加入含有标签的界面

			} else if (-1 == status) {// 调试处理，如请求参数不对
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (-2 == status) {// 服务器异常，用于调试
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			}

			break;
		case ActionId.PERSONAL_SELECT_HOBBY:// “我的爱好”列表
			if (0 == status) {//
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (status == 1) {
				if (response.getData() == null)
					return;
				mlistt = (List<HobbyBean>) response.getData();
				mValss.clear();
				if (null != mlistt && mlistt.size() > 0) {

					for (int i = 0; i < mlistt.size(); i++) {
						mValss.add(mlistt.get(i).getHobby());
					}

				}

				nFlowLayout = (TagFlowLayout) loveView
						.findViewById(R.id.getjob_resume_love);

				love_tagAdapter = new TagAdapter<String>(mValss) {
					final LayoutInflater mInflaterr = LayoutInflater
							.from(ResumeManagerActivity.this);

					@SuppressLint("NewApi")
					@Override
					public View getView(FlowLayout parent, final int position,
							final String s) {
						final View rl = mInflaterr.inflate(
								R.layout.item_person_list, nFlowLayout, false);
						final TextView tv = (TextView) rl
								.findViewById(R.id.text_item_personal_skill);

						try {
							if (!"".equals(s)) {
								if (s.contains("#")) {

									// 获取最后"#"的位置
									int lastIndex = s.lastIndexOf("#");
									// 获取爱好内容
									tv.setText(s.subSequence(0, lastIndex));
									// 获取背景颜色值
									String blockColor = s.substring(lastIndex,
											s.length());
									int spec = MeasureSpec.makeMeasureSpec(0,
											MeasureSpec.UNSPECIFIED);
									tv.measure(spec, spec);
									setBackgroundRounded(tv.getMeasuredWidth(),
											tv.getMeasuredHeight(), tv,
											Color.parseColor(blockColor));
								} else {
									tv.setText(s);
								}

							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						tv.setPadding(30, 4, 30, 4);
						return rl;
					}
				};
				nFlowLayout.setAdapter(love_tagAdapter);
				rl_resume_manager_harvest_honor_info_love.removeAllViews();// 清除
				rl_resume_manager_harvest_honor_info_love.addView(loveView);// 加入含有标签的界面

			} else if (-1 == status) {// 调试处理，如请求参数不对
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (-2 == status) {// 服务器异常，用于调试
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			}

			break;
		case ActionId.SELECT_JOB_INTENT:// 求职意向
			if (0 == status) {// 业务级的异常---用户提示如用户密码错误
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (status == 1) {

				if (response.getData() != null) {
					jobBean = (JobIntentBean) response.getData();
					initJobIntent(jobBean);
				}
			} else if (-1 == status) {// 调试处理，如请求参数不对
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (-2 == status) {// 服务器异常，用于调试
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			}
			break;
		case ActionId.SELECT_WORK_STATE:// 工作经历
			if (0 == status) {
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (status == 1) {
				if (response.getData() == null)
					return;
				List<StudyStateBean> workList = (List<StudyStateBean>) response
						.getData();
				ListView listView = (ListView) workView
						.findViewById(R.id.item_resume_listview_body);
				final ArrayList<ResumeItemBean> list = new ArrayList<ResumeItemBean>();
				for (int i = 0; workList != null && i < workList.size(); i++) {
					ResumeItemBean bean = new ResumeItemBean();
					bean.setTime(workList.get(i).getStatusTime());
					bean.setName(workList.get(i).getPosition1name());
					bean.setVitaeTypeId(workList.get(i).getVitaeTypeId());
					bean.setVitaeId(workList.get(i).getVitaeId());
					list.add(bean);
				}
				ResumeHarvestAdapter adapter = new ResumeHarvestAdapter(list,
						ResumeManagerActivity.this);
				listView.setAdapter(adapter);
				setGridViewHeightBasedOnChildren(listView);
				rl_resume_manager_work_state_info.removeAllViews();
				rl_resume_manager_work_state_info.addView(workView);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String lx = list.get(position).getVitaeTypeId();
						String lx_id = list.get(position).getVitaeId();
						Bundle bundle = new Bundle();
						if ("3".equals(lx)) {
							bundle.putString("workFullId", lx_id);
							bundle.putString("flag", "3");// "3"查看
							editor.putString("workFullId", lx_id);
							editor.putString("from", "ResumeManagerActivity");
							editor.putString("full_flag", "3");
							editor.commit();
							T_Intent.startActivity(ResumeManagerActivity.this,
									FulltimeWorkState.class, bundle);

						} else if ("4".equals(lx)) {
							bundle.putString("workPartId", lx_id);
							bundle.putString("flag", "3");// "3"查看
							editor.putString("from", "ResumeManagerActivity");
							editor.putString("workPartId", lx_id);
							editor.putString("partt_flag", "3");
							editor.commit();
							T_Intent.startActivity(ResumeManagerActivity.this,
									ParttimeWorkState.class, bundle);
						}
					}
				});
			} else if (-1 == status) {// 调试处理，如请求参数不对
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (-2 == status) {// 服务器异常，用于调试
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			}
			break;
		case ActionId.SELECT_STUDY_STATE:// 学习经历
			if (0 == status) {// 业务级的异常---用户提示如用户密码错误
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (status == 1) {
				if (response.getData() == null)
					return;
				List<StudyStateBean> studyList = (List<StudyStateBean>) response
						.getData();
				ListView listView = (ListView) studyView
						.findViewById(R.id.item_resume_listview_body);
				final ArrayList<ResumeItemBean> list = new ArrayList<ResumeItemBean>();
				for (int i = 0; studyList != null && i < studyList.size(); i++) {
					ResumeItemBean bean = new ResumeItemBean();

					bean.setTime(studyList.get(i).getStatusTime());
					bean.setName(studyList.get(i).getPosition1name());
					bean.setVitaeId(studyList.get(i).getVitaeId());
					bean.setVitaeTypeId(studyList.get(i).getVitaeTypeId());
					list.add(bean);
				}
				ResumeHarvestAdapter adapter = new ResumeHarvestAdapter(list,
						ResumeManagerActivity.this);
				listView.setAdapter(adapter);
				setGridViewHeightBasedOnChildren(listView);
				rl_resume_manager_study_state_info.removeAllViews();
				rl_resume_manager_study_state_info.addView(studyView);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String lx = list.get(position).getVitaeTypeId();
						String lx_id = list.get(position).getVitaeId();
						Bundle bundle = new Bundle();
						if ("1".equals(lx)) {
							bundle.putString("studyId", lx_id);
							bundle.putString("flag", "3");// "3"查看
							T_Intent.startActivity(ResumeManagerActivity.this,
									AcademicEducationActivity.class, bundle);

						} else if ("2".equals(lx)) {
							bundle.putString("trainId", lx_id);
							bundle.putString("flag", "3");// "3"查看
							T_Intent.startActivity(ResumeManagerActivity.this,
									TrainingStudiesActivity.class, bundle);
						}
					}
				});
			} else if (-1 == status) {// 调试处理，如请求参数不对
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (-2 == status) {// 服务器异常，用于调试
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			}
			break;

		case ActionId.REFRESH_SELF_RESUME:
			if (0 == status) {// 业务级的异常---用户提示如用户密码错误
				// WT_Toast.showToast(mContext, response.getMsg(),
				// Toast.LENGTH_SHORT);
			} else if (1 == status) {// 查询成功，有值，没值
				// 正常处理
				Toast.makeText(ResumeManagerActivity.this, response.getMsg(),
						Toast.LENGTH_SHORT).show();
			} else if (-1 == status) {// 调试处理，如请求参数不对
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			} else if (-2 == status) {// 服务器异常，用于调试
				WT_Toast.showToast(mContext, response.getMsg(),
						Toast.LENGTH_SHORT);
			}

			break;
		}

	}

	// 修改性别、身份网络请求
	private void httpUpdataName() {
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("userId", ThinkCooApp.mUserBean.getUserId());
		params.put("fullName", txt_resume_manager_username.getText().toString());// 用户姓名
		params.put("sex", ac_state_study_school_sex.getText().toString());// 性别
		String url = ThinkCooApp.getInstance().webNodes
				.get(URL.PERSONAL_PREFIX) + "updatepersonmsg.json";
		TypeToken<BaseResponse<String>> typeToken = new TypeToken<BaseResponse<String>>() {
		};
		RequestNetBean<?, ?> requestNet = new RequestNetBean<BaseResponse<String>, ResumeManagerActivity>(
				ResumeManagerActivity.this, url, params,
				ActionId.PERSONAL_UPDATA_FULLNAME, true, 1, typeToken,
				ResumeManagerActivity.class);
		ThinkCooApp.getInstance().get(requestNet);
	}

	/**
	 * 刷新简历 Description: 自己填写
	 */
	public void refreshResume() {
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("userId", ThinkCooApp.mUserBean.getUserId());
		String url = ThinkCooApp.getInstance().webNodes.get(URL.GETJOB_PREFIX)
				+ "refreshjob.json";
		TypeToken<BaseResponse<String>> typeToken = new TypeToken<BaseResponse<String>>() {
		};
		RequestNetBean<?, ?> requestNet = new RequestNetBean<BaseResponse<String>, ResumeManagerActivity>(
				ResumeManagerActivity.this, url, params,
				ActionId.REFRESH_SELF_RESUME, false, 1, typeToken,
				ResumeManagerActivity.class);
		ThinkCooApp.getInstance().get(requestNet);
	}

	/**
	 * TextView 画圆角背景 Description: 自己填写
	 * 
	 * @param w
	 * @param h
	 * @param v
	 * @param mBgColor
	 */
	public void setBackgroundRounded(int w, int h, View v, int mBgColor) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		double dH = (metrics.heightPixels / 100) * 1.5;
		int iHeight = (int) dH;

		iHeight = mCornerSize;

		Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bmp);

		Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
		paint.setAntiAlias(true);
		paint.setColor(mBgColor);
		RectF rec = new RectF(0, 0, w, h);
		c.drawRoundRect(rec, iHeight, iHeight, paint);
		v.setBackgroundDrawable(new BitmapDrawable(getResources(), bmp));
	}

	public void setGridViewHeightBasedOnChildren(ListView listView) {
		// 获取GridView对应的Adapter

		if (listView == null) {
			return;
		}
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			// listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 通过身份code获取身份文字 Description: 自己填写
	 * 
	 * @param certificateKey
	 * @return
	 */
	private String getCertificateName(String certificateKey) {
		String certificateKeyString = "";
		if (!TextUtils.isEmpty(certificateKey)) {
			if (("0").equals(certificateKey)) {
				certificateKeyString = getString(R.string.personal_mybasicinfo_sfzz);
			} else if (("1").equals(certificateKey)) {
				certificateKeyString = getString(R.string.personal_mybasicinfo_hzz);
			} else if (("6").equals(certificateKey)) {
				certificateKeyString = getString(R.string.personal_mybasicinfo_lxz);
			} else if (("8").equals(certificateKey)) {
				certificateKeyString = getString(R.string.personal_mybasicinfo_qtz);
			}
		}
		return certificateKeyString;
	}
}