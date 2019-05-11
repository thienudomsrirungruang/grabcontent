$(document).ready(init);

function init(){
    // get params
    url = $(location).attr('href');
    splitUrl = url.split(' ')
    cartoonName = splitUrl[1]
    chapter = splitUrl[2]

    $.ajax({
        method: 'GET',
        url: '/api/cartoon/getPages/cartoonName/chapter'
    }).done(function(listOfUrls){
        console.log(listOfUrls);
    });
}