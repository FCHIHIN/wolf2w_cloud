var vue = new Vue({
    el:"#app",
    data:{
        question:{}
    },
    methods:{

    },
    mounted:function () {
        var param = getParams();
        var id = param.id;
            ajaxGet("article", "/questions/get", {id}, function (data){
                console.log(data);
                vue.question = data.data;
            })
    }
});

