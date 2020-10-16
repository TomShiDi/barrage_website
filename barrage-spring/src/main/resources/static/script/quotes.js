let Quotes = {
    preIndex: Math.floor(Math.random() * 54),
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
            // $("div[quotes-flag] pre").text(JSON.stringify(e.data.quotesContent));
            // debugger;
            console.log(e.data.quotesContent);

            document.querySelector("div[quotes-flag] pre").innerText = e.data.quotesContent;
            // document.querySelector("div[quotes-flag] pre").innerText = "akjsdhajkhdkjak\r\namdnkakdja";
            that.preDate = Date.now();
            that.preIndex++;
        }
    })
};

export {
    Quotes
};