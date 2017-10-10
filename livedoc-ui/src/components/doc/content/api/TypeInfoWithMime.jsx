// @flow
import * as React from 'react';
import { TypeInfo } from '../common/TypeInfo';
import type { TypeInfoProps } from '../common/TypeInfo';

export type TypeInfoWithMimeProps = TypeInfoProps & {
  mimeTypes: ?(string[]),
}

export const TypeInfoWithMime = ({mimeTypes, ...props}: TypeInfoWithMimeProps) => {

  const typeInfo = <TypeInfo {...props}/>;

  if (mimeTypes) {
    return <span>{typeInfo} as {joinAsCodeBlocks(mimeTypes, ', ')}</span>
  }
  return typeInfo;
};

function joinAsCodeBlocks(elements: string[], delimiter: string) {
  const codeElems = elements.map(e => <code>{e}</code>);
  let result = [];
  for (let e of codeElems) {
    result.push(e);
    result.push(delimiter);
  }
  if (result.length > 0) {
    result.pop();
  }
  return result;
}