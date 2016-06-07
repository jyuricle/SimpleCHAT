/**
 * Created by yurii_krat on 31.05.16.
 */
var user;

function indexPage() {
    $.ajax({
        url: "session",
        type: 'POST',
        dataType: "json",

        success: function (data) {
            var content;
            if (data != null) {
                if (data.nickname != null) {
                    user = data.nickname;
                    showMessages();
                }
                else {
                    user = null;
                    content = "<p>Oppps... Some error occurred! Sorry for inconvenience:(</p>"
                }
            }
            else {
                user = null;
                content = "<p>For viewing the content of chat, please, log in or sign up!</p>\n" +
                    "<p><a href=\"pages/authentication.html\">Log In</a></p>\n" +
                    "<p><a href=\"pages/registration.html\">Sign Up</a></p>";
            }
            $(".container").html(content);
        },
        error: function (data, status, er) {
            alert("Error occurred!\n Error: " + er + "\nStatus: " + status);
        }
    });
};

function showMessages() {
    if (user != null) {
        $.ajax({
            url: "messages",
            type: 'POST',
            dataType: "json",

            success: function (data) {
                var content = "<p>Hello, <span class=\"user\">" + user + "</span>!" +
                    "<a href = \"/pages/authentication.html\">Log Out</a></p>";
                if (data != null) {
                    var time, last = "";
                    content += "<div class=\"all-messages\" onload=\"this.scrollTop = 9999;\">\n";
                    $.each(data, function(index, message) {
                        time = new Date(message.date);
                        if (index == data.length - 1) {
                        content += "<a id=\"last\"></a>"
                            last = "id=\"#last\""
                        }
                        content += "<div class=\"message\"" + last + "\n><p class=\"user\">" +
                            message.user.nickname + "</p>\n" +
                            "<p>" + time.toLocaleString() + "</p>\n" + "<p class=\"text\">"
                            + message.text + "</p>\n" + "</div>\n";
                    })
                    content += "</div>\n";
                    content += "<div class=\"send\"><form method=\"POST\" action=\"\\add_message\">\n" +
                        "<textarea name=\"new-message\" cols=\"70\" rows=\"8\"></textarea>\n" +
                        "<p><input type=\"submit\" value=\"Send\" name=\"send\" /></p>" +
                        "</form>\n</div>\n";
                    $(".container").html(function() {
                        return content;
                    });
                    document.getElementById("last").scrollIntoView();
                }

            },
            error: function (data, status, er) {
                alert("Error occurred after showing messages!\n Error: " + er + "\nStatus: " + status);
            }
        })
    }
}

window.onload = function() {
    indexPage();
    setInterval(showMessages, 5000)
}