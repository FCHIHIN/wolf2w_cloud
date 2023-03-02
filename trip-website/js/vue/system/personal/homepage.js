var user = getUserInfo();

var vue = new Vue({
    el:"#app",
    data:{
        //userId对应的用户--窝的主人
        owner:{},
        isFollow:null,  //是否关注， true已经关注  false 未关注
        follows:[],
        followNum:null,
    },
    methods:{
        follow:function () {
            var param = getParams();
            var userId = param.userId;
            //关注与取消关注--接口实现
            ajaxPost("member", "/follows/follow",{userId:userId}, function (data) {
                var map = data.data;
                vue.isFollow = map.result;
                if(map.result){
                    popup("关注成功");
                }else{
                    popup("已取消关注");
                }
            })
        }
    },
    filters:{

    },
    mounted: function () {
        var param = getParams();
        ajaxGet("member", "/follows/detail", {userId: param.userId}, function (data) {
            console.log(data);
            vue.owner = data.data;
        });
        //当前登录用户是否关注该用户了？--isFollow
        //该用户关注人数--followNum
        ajaxGet("member", "/follows/getdata", {userId: param.userId}, function (data) {
            console.log(data);
            var map = data.data;
            vue.isFollow = map.isFollow;
            vue.followNum = map.followNum;
        });
        //userId对应的用户关注列表---follows
        ajaxGet("member", "/follows/list", {userId: param.userId}, function (data) {
            console.log(data.data,1);
            vue.follows = data.data;
        });
    }
});

