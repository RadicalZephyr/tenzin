(ns {{name}}.page
    (:require [hiccup.element :as e]
              [hiccup.page :as p]))

(def page
  (p/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1, shrink-to-fit=no"}]
    [:link
     {:rel "stylesheet"
      :href "https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
      :integrity "sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M"
      :crossorigin "anonymous"}]]

   [:body
    [:div.container
     [:div.row
      [:div#app.col-auto.ml-auto.mr-auto]]]

    [:script
     {:src "https://code.jquery.com/jquery-3.2.1.slim.min.js"
      :integrity "sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
      :crossorigin "anonymous"}]
    [:script
     {:src "https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
      :integrity "sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
      :crossorigin "anonymous"}]
    [:script
     {:src "https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
      :integrity "sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
      :crossorigin "anonymous"}]
    (e/javascript-tag "")]))
