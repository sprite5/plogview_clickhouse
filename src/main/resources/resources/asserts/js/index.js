/** yunfile.js*/
"use strict";

/**
 *
 * @returns {AppData}
 */
function app() {
    return{
        data:_init(),
        search:function(){
            _search(this.data)
        },
        /**
         *
         * @param isPrev <boolean>
         */
        changePage:function(isPrev){
            _changePage(isPrev,this.data)
        },
    }
}

function _init(){
    const now =  DateTime()


    return {
        searchForm:{
            startDate:now.format('YYYY-MM-DD'),
            startTime:now.add(-1,"hour").format("HH:mm"),
            endDate:now.format('YYYY-MM-DD'),
            endTime:now.format("HH:mm"),
            level:"",
            server:"gps_prod",
            traceId:"",
            searchKey:"",
            pageNum:1,
            pageSize:20
        },
        searchParams:{},
        tableData:{
            list:[]
        },
    }
}

function _search(data){
    let searchForm = data.searchForm;
    let startDateTime = searchForm.startDate + " " + searchForm.startTime;
    let endDateTime = searchForm.endDate + " " + searchForm.endTime;
    if(!DateTime(startDateTime).isValid() || !DateTime(endDateTime).isValid()){
        alert("time error");return;
    }
    data.searchParams = {
        startDateTime:startDateTime,
        endDateTime:endDateTime,
        ... searchForm
    }

    axios.post("/api/searchLog", data.searchParams)
        .then(function (response){
            data.tableData = {...response.data}
        })
    console.log(data)

}




function  _changePage(isPrev,data){

}
