module Json where

import Data.Either (Either)
import Data.Foreign (MultipleErrors)
import Data.Generic.Rep (class Generic)
import Data.Generic.Rep.Show (genericShow)
import Data.StrMap (StrMap)
import Prelude (class Show, (<$>))
import Simple.JSON (class ReadForeign, readImpl, readJSON)

newtype Livedoc = Livedoc
  { version :: String
  , basePath :: String
  , apis :: Apis
  }

type Apis = StrMap (Array ApiDoc)

newtype ApiDoc = ApiDoc
  { description :: String
  , group :: String
  }

derive instance genericLivedoc :: Generic Livedoc _

instance showLivedoc :: Show Livedoc where
  show = genericShow

derive instance genericApiDoc :: Generic ApiDoc _

instance showApiDoc :: Show ApiDoc where
  show = genericShow

instance readForeignApiDoc :: ReadForeign ApiDoc where
  readImpl f = ApiDoc <$> readImpl f

instance readForeignLivedoc :: ReadForeign Livedoc where
  readImpl f = Livedoc <$> readImpl f

readLivedoc :: String -> Either MultipleErrors Livedoc
readLivedoc = readJSON

