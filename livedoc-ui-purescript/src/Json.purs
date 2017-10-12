module Json where

import Data.Either (Either)
import Data.Foreign (MultipleErrors)
import Data.Generic.Rep (class Generic)
import Data.Generic.Rep.Show (genericShow)
import Prelude (class Show, (<$>))
import Simple.JSON (class ReadForeign, readImpl, readJSON)

newtype Livedoc = Livedoc
  { version :: String
  , basePath :: String
  }

derive instance genericLivedoc :: Generic Livedoc _

instance showLivedoc :: Show Livedoc where
  show = genericShow

instance readForeignLivedoc :: ReadForeign Livedoc where
  readImpl f = Livedoc <$> readImpl f

readLivedoc :: String -> Either MultipleErrors Livedoc
readLivedoc = readJSON
