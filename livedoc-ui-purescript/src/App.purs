module App where

import Prelude (discard, ($))
import Pux.DOM.HTML (HTML)
import State (Event, State)
import Text.Smolder.HTML (div, h1)
import Text.Smolder.HTML.Attributes (className)
import Text.Smolder.Markup (text, (!))
import View.InlineForm as InlineForm

view :: State -> HTML Event
view state =
  div ! className "App" $ do
    div ! className "App-header" $ do
      h1 ! className "App-title" $ text "Livedoc"
    InlineForm.view state
