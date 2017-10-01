module View.InlineForm where

import Prelude (discard, ($))
import Pux.DOM.HTML (HTML)
import State (Event, State)
import Text.Smolder.HTML (button, div, input)
import Text.Smolder.HTML.Attributes (type')
import Text.Smolder.Markup (text, (!))

view :: State -> HTML Event
view state = div do
  input ! type' "text"
  button $ text "Get Doc"
