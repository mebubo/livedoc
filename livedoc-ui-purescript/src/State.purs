module State where

import Pux.DOM.Events (DOMEvent)

type State =
  { form :: String
  }

type Url = String

data Event
  = FormUpdate String
  | FormSubmit DOMEvent
  | RequestDoc Url
  | ReceiveDoc String
