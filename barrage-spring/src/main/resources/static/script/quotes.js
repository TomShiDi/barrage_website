let Quotes = {
    preIndex:0,
    preDate:null,
    currDate:null,
};

Quotes.getOne = function () {
    let that = this;
    $.ajax({
        url: "quotes/getOne?index=" + (this.preIndex + 1),
        type: "get",
        dataType: "json",
        success: function (e) {
            $("div[quotes-flag]").text(JSON.stringify(e.data.quotesContent));
            that.preIndex++;
        }
    })
};

export {
    Quotes
};