$(document).ready(init);

nextPageToLoad = 0
loading = false;
html=''

function init(){
        loadNextPage();
}



function loadNextPage(){
    loading = true;
    $.ajax({
        method: 'GET',
        url: '/api/cartoon/byViews',
        data: {
            page: nextPageToLoad
        }
    }).done(function(listOfCartoons){
        listOfCartoons.forEach(function(x){
            html += '<div class="square-wrapper"><div class="square"><a href="/"><img class="image" src="' + x.firstPageUrl + '"/></a></div><div class=description>' + x.cartoonName + '<br/>Episode ' + x.chapter + '<br/>' + x.views + ' Views</div></div></div>';
        });
        $('#square-container').html(html);
        nextPageToLoad++;
        loading = false;
    })
}



$(window).scroll(function(){
    if  ( ($(document).height() - $(window).height()) - $(window).scrollTop() < 1000 && !loading){
        loadNextPage();
    }
});
