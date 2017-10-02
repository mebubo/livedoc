module State where

import Pux.DOM.Events (DOMEvent)

type State = {
  form :: String
  }

data Event = FormUpdate String
  | FormSubmit DOMEvent
