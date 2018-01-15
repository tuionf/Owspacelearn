package com.example.tuionf.owspacelearn.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tuionf.owspacelearn.R;
import com.example.tuionf.owspacelearn.model.entity.Item;
import com.example.tuionf.owspacelearn.view.activity.AudioDetailActivity;
import com.example.tuionf.owspacelearn.view.activity.DetailActivity;
import com.example.tuionf.owspacelearn.view.activity.VideoDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author tuionf
 * @date 2017/12/26
 * @email 596019286@qq.com
 * @explain
 */

public class MainFragment extends Fragment{

    @BindView(R.id.type_container) LinearLayout typeContainer;
    @BindView(R.id.home_advertise_iv) ImageView homeAdvertiseIv;
    @BindView(R.id.pager_content) RelativeLayout pagerContent;
    @BindView(R.id.topPanel) RelativeLayout topPanel;
    @BindView(R.id.image_iv) ImageView imageIv;
    @BindView(R.id.image_type) ImageView imageType;
    @BindView(R.id.download_start_white) ImageView downloadStartWhite;
    @BindView(R.id.typePanel) RelativeLayout typePanel;
    @BindView(R.id.type_tv) TextView typeTv;
    @BindView(R.id.time_tv) TextView timeTv;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.content_tv) TextView contentTv;
    @BindView(R.id.divider) View divider;
    @BindView(R.id.author_tv) TextView authorTv;
    @BindView(R.id.comment_tv) TextView commentTv;
    @BindView(R.id.like_tv) TextView likeTv;
    @BindView(R.id.readcount_tv) TextView readcountTv;

    Unbinder unbinder;

    public MainFragment() {
    }

    public static Fragment  instance(Item item){
        Fragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("item", item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_main_page,container,false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Item item = getArguments().getParcelable("item");
        final int model = Integer.valueOf(item.getModel());
        if (model == 5) {
            pagerContent.setVisibility(View.GONE);
            Glide.with(getContext()).load(item.getThumbnail()).into(homeAdvertiseIv);
            homeAdvertiseIv.setVisibility(View.VISIBLE);
        }else {
            pagerContent.setVisibility(View.VISIBLE);
            homeAdvertiseIv.setVisibility(View.GONE);
            Glide.with(getContext()).load(item.getThumbnail()).into(imageIv);
            commentTv.setText(item.getComment());
            likeTv.setText(item.getGood());
            readcountTv.setText(item.getView());
            titleTv.setText(item.getTitle());
            contentTv.setText(item.getExcerpt());
            authorTv.setText(item.getAuthor());
            typeTv.setText(item.getCategory());

            switch(model){
                //视频
                case 2:
                    imageType.setImageResource(R.mipmap.library_video_play_symbol);
                    imageType.setVisibility(View.VISIBLE);
                    downloadStartWhite.setVisibility(View.GONE);
                    break;
                //音频
                case 3:
                    imageType.setImageResource(R.mipmap.library_voice_play_symbol);
                    imageType.setVisibility(View.VISIBLE);
                    downloadStartWhite.setVisibility(View.VISIBLE);
                    break;
                default:
                    imageType.setVisibility(View.GONE);
                    downloadStartWhite.setVisibility(View.GONE);
                    break;
            }
        }

        typeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch(model){
                    case 5:
                        Uri uri = Uri.parse(item.getHtml5());
                        intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra("item",item);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), VideoDetailActivity.class);
                        intent.putExtra("item",item);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getActivity(), AudioDetailActivity.class);
                        intent.putExtra("item",item);
                        startActivity(intent);
                        break;
                    default:
                        intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra("item",item);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
