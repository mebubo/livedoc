// @flow
import * as React from 'react';
import { Table } from 'react-bootstrap';
import type { ApiObjectDoc } from '../../../../../model/livedoc';
import { EnumValueRow } from './EnumValueRow';

export type EnumTypeDetailsProps = {
  typeDoc: ApiObjectDoc
}

export const EnumTypeDetails = ({typeDoc}: EnumTypeDetailsProps) => {

  const values = (typeDoc.allowedvalues: string[]);
  const valueRows = values.map(v => <EnumValueRow key={v} value={v} description=""/>);

  return <Table striped>
    <thead>
    <tr>
      <th>Value</th>
      <th>Description</th>
    </tr>
    </thead>
    <tbody>
    {valueRows}
    </tbody>
  </Table>;
};
