package com.ego.ch.helper;



import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ego.ch.interfaces.RewardAnimationListener;
import com.ego.classhero.R;

public class RewardViewHelper {

	Activity a;

	RewardAnimationListener l;

	ValueAnimator scaleUpX, scaleUpY, scaleDownX, scaleDownY, transitionX,
			transitionY;

	SoundPool pool;
	int coins;

	Fragment f;

	Handler handler;

	RewardAnimationListener listener;

	public RewardViewHelper(Fragment f, SoundPool pool,
			RewardAnimationListener listener) {
		this.pool = pool;
		this.f = f;
		this.listener = listener;
		coins = pool.load(f.getActivity(), R.raw.coin_shake, 1);

	}

	public void animateThreeCoins(final ImageView ivStudentCoin1,
			final ImageView ivStudentCoin2, final ImageView ivStudentCoin3,
			final ImageView ivCoinSack, final TextView currentScore,
			final TextView tvXP, final ProgressBar lvlProgress)
			throws InterruptedException {

		scaleUpX = ObjectAnimator.ofFloat(ivStudentCoin1, "scaleX", .1f, 1f);
		scaleUpY = ObjectAnimator.ofFloat(ivStudentCoin1, "scaleY", .1f, 1f);

		scaleUpX.setDuration(200);
		scaleUpY.setDuration(200);

		f.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {

				handler = new Handler();

				startXPAnimation(tvXP, lvlProgress);

				scaleFirstCoin(ivStudentCoin1);

				scaleSecondCoin(ivStudentCoin2, 250);

				scaleThirdCoin(ivStudentCoin3, 400);

				translateFirstCoin(ivStudentCoin1, currentScore, 700);

				translateSecondCoin(ivStudentCoin2, currentScore, 1200);

				translateThirdCoin(ivStudentCoin3, currentScore, 1500);

				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						listener.onAnimationEnd();

					}
				}, 2000);

			}
		});

	}

	public void animateTwoCoins(final ImageView ivStudentCoin2,
			final ImageView ivStudentCoin3, ImageView ivCoinSack,
			final TextView currentScore, final TextView tvXP,
			final ProgressBar lvlProgress) {

		scaleUpX = ObjectAnimator.ofFloat(ivStudentCoin3, "scaleX", .1f, 1f);
		scaleUpY = ObjectAnimator.ofFloat(ivStudentCoin3, "scaleY", .1f, 1f);

		scaleUpX.setDuration(200);
		scaleUpY.setDuration(200);

		f.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {

				handler = new Handler();

				startXPAnimation(tvXP, lvlProgress);

				scaleFirstCoin(ivStudentCoin2);

				scaleThirdCoin(ivStudentCoin3, 500);

				translateSecondCoin(ivStudentCoin2, currentScore, 1200);

				translateThirdCoin(ivStudentCoin3, currentScore, 1500);

				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						listener.onAnimationEnd();

					}
				}, 2000);

			}
		});

	}

	public void animateOneCoin(final ImageView ivStudentCoin1,
			final ImageView ivCoinSack, final TextView currentScore,
			final TextView tvXP, final ProgressBar lvlProgress) {

		ivCoinSack.bringToFront();
		
		scaleUpX = ObjectAnimator.ofFloat(ivStudentCoin1, "scaleX", .1f, 1f);
		scaleUpY = ObjectAnimator.ofFloat(ivStudentCoin1, "scaleY", .1f, 1f);

		scaleUpX.setDuration(200);
		scaleUpY.setDuration(200);

		f.getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				ivCoinSack.bringToFront();

				handler = new Handler();

				startXPAnimation(tvXP, lvlProgress);

				scaleFirstCoin(ivStudentCoin1);

				translateFirstCoin(ivStudentCoin1, currentScore, 1000);

				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						listener.onAnimationEnd();

					}
				}, 1500);

			}
		});

	}

	private void startXPAnimation(final TextView tvXP,
			final ProgressBar lvlProgress) {

		final AnimatorSet xpBouncer = new AnimatorSet();
		tvXP.setVisibility(View.VISIBLE);
		final ValueAnimator xpTranslate = ObjectAnimator.ofFloat(tvXP,"TranslationY", -250);
		final ValueAnimator fadeout = ObjectAnimator.ofFloat(tvXP, "Alpha", 1f, 0f);
		xpTranslate.setDuration(2000);
		xpBouncer.play(fadeout).after(xpTranslate);
		xpBouncer.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
			

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {

				f.getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
		
						
						
						tvXP.setVisibility(View.INVISIBLE);
						
						xpTranslate.reverse();
						fadeout.reverse();

					}
				});

			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});

		xpBouncer.start();
		
	}

	private void scaleFirstCoin(ImageView v) {
		AnimatorSet bouncer1 = new AnimatorSet();
		bouncer1.play(scaleUpX).with(scaleUpY);
		v.setVisibility(View.VISIBLE);
		bouncer1.start();
	}

	private void scaleSecondCoin(final ImageView v, int millis) {
		final AnimatorSet bouncer2 = new AnimatorSet();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				scaleUpX = ObjectAnimator.ofFloat(v, "scaleX", .1f, 1f);
				scaleUpY = ObjectAnimator.ofFloat(v, "scaleY", .1f, 1f);
				bouncer2.play(scaleUpX).with(scaleUpY);
				v.setVisibility(View.VISIBLE);
				bouncer2.start();

			}
		}, millis);

	}

	private void scaleThirdCoin(final ImageView v, int millis) {
		final AnimatorSet bouncer3 = new AnimatorSet();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				scaleUpX = ObjectAnimator.ofFloat(v, "scaleX", .1f, 1f);
				scaleUpY = ObjectAnimator.ofFloat(v, "scaleY", .1f, 1f);
				bouncer3.play(scaleUpX).with(scaleUpY);
				v.setVisibility(View.VISIBLE);
				bouncer3.start();

			}
		}, millis);

	}

	private void translateFirstCoin(final ImageView v,
			final TextView currentScore, int millis) {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				final ValueAnimator transitionX = ObjectAnimator.ofFloat(v, "TranslationY", 190);
				final ValueAnimator transitionY = ObjectAnimator.ofFloat(v, "TranslationX", -30);
				final ValueAnimator scaleDownX = ObjectAnimator.ofFloat(v, "scaleX", 1f, .1f);
				final ValueAnimator scaleDownY = ObjectAnimator.ofFloat(v, "scaleY", 1f, .1f);

				transitionX.setDuration(300);
				transitionY.setDuration(300);

				final AnimatorSet trans1 = new AnimatorSet();

				trans1.play(transitionX).with(transitionY).with(scaleDownX).with(scaleDownY);
				trans1.addListener(new AnimatorListener() {

					@Override
					public void onAnimationStart(Animator animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animator animation) {


					}

					@Override
					public void onAnimationEnd(Animator animation) {
						f.getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {
								v.setVisibility(View.INVISIBLE);
								

								
								transitionX.reverse();
								transitionY.reverse();
								scaleDownX.reverse();
								scaleDownY.reverse();

								pool.play(coins, 50, 100, 1, 0, 1f);

								// ivCoinSack.startAnimation(pulse);
								currentScore.setText(String.valueOf(Integer
										.parseInt(currentScore.getText()
												.toString()) + 1));

							}

						});

					}

					@Override
					public void onAnimationCancel(Animator animation) {
						// TODO Auto-generated method stub

					}

				});
				trans1.start();

			}
		}, millis);

	}


	private void translateSecondCoin(final ImageView v,
			final TextView currentScore, int millis) {

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				final ValueAnimator transitionX = ObjectAnimator.ofFloat(v, "TranslationY", 190);
				final ValueAnimator transitionY = ObjectAnimator.ofFloat(v, "TranslationX", 65);
				final ValueAnimator scaleDownX = ObjectAnimator.ofFloat(v, "scaleX", 1f, .1f);
				final ValueAnimator scaleDownY = ObjectAnimator.ofFloat(v, "scaleY", 1f, .1f);

				transitionX.setDuration(300);
				transitionY.setDuration(300);

				final AnimatorSet trans2 = new AnimatorSet();
				trans2.play(transitionX).with(transitionY).with(scaleDownX).with(scaleDownY);

				trans2.addListener(new AnimatorListener() {

					@Override
					public void onAnimationStart(Animator animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animator animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animator animation) {
						f.getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {
								
								v.setVisibility(View.INVISIBLE);

								transitionX.reverse();
								transitionY.reverse();
								scaleDownX.reverse();
								scaleDownY.reverse();

								pool.play(coins, 50, 100, 1, 0, 1f);

								// ivCoinSack.startAnimation(pulse);
								currentScore.setText(String.valueOf(Integer
										.parseInt(currentScore.getText()
												.toString()) + 1));
								
							}
						});
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						// TODO Auto-generated method stub

					}
				});
				trans2.start();

			}
		}, millis);

	}

	private void translateThirdCoin(final ImageView v,
			final TextView currentScore, int millis) {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				final ValueAnimator transitionX = ObjectAnimator.ofFloat(v, "TranslationY", 190);
				final ValueAnimator transitionY = ObjectAnimator.ofFloat(v, "TranslationX", -125);
				final ValueAnimator scaleDownX = ObjectAnimator.ofFloat(v, "scaleX", 1f, .1f);
				final ValueAnimator scaleDownY = ObjectAnimator.ofFloat(v, "scaleY", 1f, .1f);

				transitionX.setDuration(300);
				transitionY.setDuration(300);

				final AnimatorSet trans3 = new AnimatorSet();
				trans3.play(transitionX).with(transitionY).with(scaleDownX)
						.with(scaleDownY);
				trans3.addListener(new AnimatorListener() {

					@Override
					public void onAnimationStart(Animator animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animator animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animator animation) {
						f.getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {
								
								v.setVisibility(View.INVISIBLE);

								transitionX.reverse();
								transitionY.reverse();
								scaleDownX.reverse();
								scaleDownY.reverse();

								pool.play(coins, 50, 100, 1, 0, 1f);

								// ivCoinSack.startAnimation(pulse);
								currentScore.setText(String.valueOf(Integer
										.parseInt(currentScore.getText()
												.toString()) + 1));
								v.setVisibility(View.GONE);
							}
						});
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						// TODO Auto-generated method stub

					}
				});
				trans3.start();

			}
		}, millis);

	}

}
