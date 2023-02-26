var vue = new Vue({
    el:"#app",
    data:{
        questions:[]
    },
    methods:{
        list:function (){
            ajaxGet("article", "/questions/list", {}, function (data){
                console.log(data);
                vue.questions = data.data;
            })
        }
    },
    mounted:function () {
        //问题列表
        this.list();
    }
});

