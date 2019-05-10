$(document).ready(init);

function init(){
    html = "";
    $.ajax({
        method: 'GET',
        url: '/api/cartoon/byViews'
    }).done(function(listOfCartoons){
        listOfCartoons.forEach(function(x){
            html += '<div class="square-wrapper"><div class="square"><a href="/"><img class="image" src="' + x.firstPageUrl + '"/></a></div><div class=description>' + x.cartoonName + '<br/>Episode ' + x.chapter + '<br/>' + x.views + ' Views</div></div></div>';
        });
        $('#square-container').html(html);
    })
}