package com.example.acer.wuli.main.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.acer.wuli.R;
import com.example.acer.wuli.model.Movie;
import com.example.acer.wuli.model.User;

import org.litepal.LitePal;

import java.util.List;


/**
 * 测试添加数据界面，修改启动页来展示
 */


public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addPhoto();

        getAllMovie();


//        updateMoviePost();


//        getAllMovie();

    }

    public void getAllMovie() {
        List<Movie> movies = LitePal.findAll(Movie.class);
        for (Movie movie : movies) {
            Log.d("movie:", movie.toString());
        }
    }

    public void updateMovie() {
        Movie movie = new Movie();

        // movie
        movie.setMovieName("来电狂响");
        movie.setType("剧情 / 喜剧");
        movie.setDirectorName("于淼");
        movie.setMainActor("佟大为 / 马丽");
        movie.setSummary("七个好友聚餐，有人提议大家玩一个将手机公开的游戏：与在场所有人分享当晚收到的" +
                "每一通来电、每一条短信微信、甚至广告弹窗，由此掀开了一场啼笑皆非的情感风暴。数字时代，" +
                "小小手机隐藏着无数秘密，当聚餐局变成“揭秘局”，当通讯工具化身定时炸弹，嬉笑打闹之后，" +
                "甜蜜情侣和模范夫妻、多年死党之间还能真诚相待、美好如初吗？这场刺激的勇敢者游戏，你敢玩吗？");
        movie.setShow(true);
        movie.setCollectNumber(0);
        movie.setPoint(6.0);
        movie.setCommentNumber(0);
        movie.setPostID("killmobile.jpg");
        movie.save();

        // movie
        Movie movie1 = new Movie();
        movie1.setMovieName("美丽人生");
        movie1.setType("剧情 / 喜剧 / 爱情 / 战争");
        movie1.setDirectorName("罗伯托·贝尼尼");
        movie1.setMainActor("罗伯托·贝尼尼");
        movie1.setSummary("犹太青年圭多（罗伯托·贝尼尼）邂逅美丽的女教师多拉（尼可莱塔·布拉斯基），他彬彬有礼的向多拉鞠躬：“早安！公主！”。历经诸多令人啼笑皆非的周折后，天遂人愿，两人幸福美满的生活在一起。 \n" +
                "　　然而好景不长，法西斯政权下，圭多和儿子被强行送往犹太人集中营。多拉虽没有犹太血统，毅然同行，与丈夫儿子分开关押在一个集中营里。聪明乐天的圭多哄骗儿子这只是一场游戏，奖品就是一辆大坦克，儿子快乐、天真的生活在纳粹的阴霾之中。尽管集中营的生活艰苦寂寞，圭多仍然带给他人很多快乐，他还趁机在纳粹的广播里问候妻子：“早安！公主！” \n" +
                "　　法西斯政权即将倾覆，纳粹的集中营很快就要接受最后的清理，圭多编给儿子的游戏该怎么结束？他们一家能否平安的度过这黑暗的年代呢？\n"
        );
        movie1.setShow(true);
        movie1.setCollectNumber(0);
        movie1.setPoint(9.5);
        movie1.setCommentNumber(0);
        movie1.setPostID("lifeisbeautiful.jpg");
        movie1.save();


        // movie
        Movie movie2 = new Movie();
        movie2.setMovieName("千与千寻");
        movie2.setType("剧情 / 动画 / 奇幻");
        movie2.setDirectorName("宫崎骏");
        movie2.setMainActor("柊瑠美 / 入野自由");
        movie2.setSummary("千寻和爸爸妈妈一同驱车前往新家，在郊外的小路上不慎进入了神秘的隧道——他们去到了另外一个诡异世界—一个中世纪的小镇。远处飘来食物的香味，爸爸妈妈大快朵颐，孰料之后变成了猪！这时小镇上渐渐来了许多样子古怪、半透明的人。 \n" +
                "　　千寻仓皇逃出，一个叫小白的人救了他，喂了她阻止身体消 失的药，并且告诉她怎样去找锅炉爷爷以及汤婆婆，而且必须获得一份工作才能不被魔法变成别的东西。 \n" +
                "　　千寻在小白的帮助下幸运地获得了一份在浴池打杂的工作。渐渐她不再被那些怪模怪样的人吓倒，并从小玲那儿知道了小白是凶恶的汤婆婆的弟子。 \n" +
                "　　一次，千寻发现小白被一群白色飞舞的纸人打伤，为了救受伤的小白，她用河神送给她的药丸驱出了小白身体内的封印以及守封印的小妖精，但小白还是没有醒过来。 \n" +
                "　　为了救小白，千寻又踏上了她的冒险之旅。");
        movie2.setShow(false);
        movie2.setCollectNumber(0);
        movie2.setPoint(9.3);
        movie2.setCommentNumber(0);
        movie2.setPostID("chihiro.jpg");
        movie2.save();

        // movie
        Movie movie3 = new Movie();
        movie3.setMovieName("四个春天");
        movie3.setType("纪录片 / 家庭");
        movie3.setDirectorName("陆庆屹");
        movie3.setMainActor("陆运坤 / 李桂贤");
        movie3.setSummary("《四个春天》是一部以真实家庭生活为背景的纪录片。15岁离家，在异乡漂泊多年的导演以自己南方小城里的父母为主角，在四年光阴里，以一己之力记录了他们的美丽日常。在如诗的乐活慢生活图景中，影像缓缓雕刻出一个幸福家庭近二十年的温柔变迁，以及他们如何以自己的方式面对流转的时间、人生的得失起落。");
        movie3.setShow(true);
        movie3.setCollectNumber(0);
        movie3.setPoint(8.9);
        movie3.setCommentNumber(0);
        movie3.setPostID("foursprings.jpg");
        movie3.save();


//        // movie
//        Movie movie4 = new Movie();
//        movie4.setMovieName("养家之人");
//        movie4.setType("剧情 / 动画 / 家庭");
//        movie4.setDirectorName("诺拉·托梅");
//        movie4.setMainActor("莎拉·乔德利 / 索玛·查亚");
//        movie4.setSummary("故事的主人公是一个阿富汗女孩帕瓦娜，她们一家生活在塔利班的严酷统治之下，非但不准女孩上学，更规定女性必须男性陪同才可外出，违者可遭毒打。父亲本是学者，在暴政下失业，由十一岁女儿帕瓦娜陪伴，到市集代人写信。有日父亲被士兵强行带走，被关入狱。帕瓦娜为维持家计，决定剪短头发，化身小木兰，偷偷以男装打扮上街，打水买食粮，代父赚钱，亦因此眼界大开，更有同学萧希亚教她生存之道。乱世催促成长，置身强权下，却仍能抓住希望。 ");
//        movie4.setShow(true);
//        movie4.setCollectNumber(0);
//        movie4.setPoint(8.3);
//        movie4.setCommentNumber(0);
//        movie4.setPostID("thebreadwinner.jpg");
//        movie4.save();


    }

    public void updateMoviePost() {
        Movie movie = new Movie();
        String resName = getResources().getResourceName(R.drawable.bumblebee);
        movie.setPostID(resName);
        movie.update(1);

        resName = getResources().getResourceName(R.drawable.fightclub);
        movie.setPostID(resName);
        movie.update(8);

        resName = getResources().getResourceName(R.drawable.godfather);
        movie.setPostID(resName);
        movie.update(9);

        resName = getResources().getResourceName(R.drawable.whitesnake);
        movie.setPostID(resName);
        movie.update(10);

        resName = getResources().getResourceName(R.drawable.mrbig);
        movie.setPostID(resName);
        movie.update(11);


        resName = getResources().getResourceName(R.drawable.thebreadwinner);
        movie.setPostID(resName);
        movie.update(12);

        resName = getResources().getResourceName(R.drawable.killmobile);
        movie.setPostID(resName);
        movie.update(13);

        resName = getResources().getResourceName(R.drawable.lifeisbeautiful);
        movie.setPostID(resName);
        movie.update(14);

        resName = getResources().getResourceName(R.drawable.chihiro);
        movie.setPostID(resName);
        movie.update(15);

        resName = getResources().getResourceName(R.drawable.foursprings);
        movie.setPostID(resName);
        movie.update(16);


    }

    public void updateMovieCommentNumber() {
        Movie movie = new Movie();
        movie.setCommentNumber(5);
        movie.update(1);
    }


    public void addPhoto() {

        Movie movie = new Movie();

        String p1 = getResources().getResourceName(R.drawable.fightclub_photo_1);
        String p2 = getResources().getResourceName(R.drawable.fightclub_photo_2);
        String p3 = getResources().getResourceName(R.drawable.fightclub_photo_3);
        movie.setPhoto1(p1);
        movie.setPhoto2(p2);
        movie.setPhoto3(p3);
        movie.update(8);

        p1 = getResources().getResourceName(R.drawable.godfather_photo_1);
        p2 = getResources().getResourceName(R.drawable.godfather_photo_2);
        p3 = getResources().getResourceName(R.drawable.godfather_photo_3);
        movie.setPhoto1(p1);
        movie.setPhoto2(p2);
        movie.setPhoto3(p3);
        movie.update(9);

        p1 = getResources().getResourceName(R.drawable.white_snake_photo_1);
        p2 = getResources().getResourceName(R.drawable.white_snake_photo_2);
        p3 = getResources().getResourceName(R.drawable.white_snake_photo_3);
        movie.setPhoto1(p1);
        movie.setPhoto2(p2);
        movie.setPhoto3(p3);
        movie.update(10);

        p1 = getResources().getResourceName(R.drawable.mrbig_photo_1);
        p2 = getResources().getResourceName(R.drawable.mrbig_photo_2);
        p3 = getResources().getResourceName(R.drawable.mrbig_photo_3);
        movie.setPhoto1(p1);
        movie.setPhoto2(p2);
        movie.setPhoto3(p3);
        movie.update(11);


        p1 = getResources().getResourceName(R.drawable.thebreadwinner_photo_1);
        p2 = getResources().getResourceName(R.drawable.thebreadwinner_photo_2);
        p3 = getResources().getResourceName(R.drawable.thebreadwinner_photo_3);
        movie.setPhoto1(p1);
        movie.setPhoto2(p2);
        movie.setPhoto3(p3);
        movie.update(12);

        p1 = getResources().getResourceName(R.drawable.killmobile_photo_1);
        p2 = getResources().getResourceName(R.drawable.killmobile_photo_2);
        p3 = getResources().getResourceName(R.drawable.killmobile_photo_3);
        movie.setPhoto1(p1);
        movie.setPhoto2(p2);
        movie.setPhoto3(p3);
        movie.update(13);

        p1 = getResources().getResourceName(R.drawable.lifeisbeautiful_photo_1);
        p2 = getResources().getResourceName(R.drawable.lifeisbeautiful_photo_2);
        p3 = getResources().getResourceName(R.drawable.lifeisbeautiful_photo_3);
        movie.setPhoto1(p1);
        movie.setPhoto2(p2);
        movie.setPhoto3(p3);
        movie.update(14);

        p1 = getResources().getResourceName(R.drawable.chihiro_photo_1);
        p2 = getResources().getResourceName(R.drawable.chihiro_photo_2);
        p3 = getResources().getResourceName(R.drawable.chihiro_photo_3);
        movie.setPhoto1(p1);
        movie.setPhoto2(p2);
        movie.setPhoto3(p3);
        movie.update(15);

        p1 = getResources().getResourceName(R.drawable.foursprings_photo_1);
        p2 = getResources().getResourceName(R.drawable.foursprings_photo_2);
        p3 = getResources().getResourceName(R.drawable.foursprings_photo_3);
        movie.setPhoto1(p1);
        movie.setPhoto2(p2);
        movie.setPhoto3(p3);
        movie.update(16);


    }

}
