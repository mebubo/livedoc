module Main where

import App (view)
import Control.Monad.Eff (Eff)
import Prelude hiding (div)
import Pux (CoreEffects, EffModel, start)
import Pux.Renderer.React (renderToDOM)
import State (Event(..), State)

foldp :: ∀ fx. Event -> State -> EffModel State Event fx
foldp Increment n = { state: n + 1, effects: [] }
foldp Decrement n = { state: n - 1, effects: [] }

main :: ∀ fx. Eff (CoreEffects fx) Unit
main = do
  app <- start
    { initialState: 0
    , view
    , foldp
    , inputs: []
    }

  renderToDOM "#app" app.markup app.input
