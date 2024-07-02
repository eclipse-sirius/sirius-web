/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import { useTranslation } from 'react-i18next';
import { ImageRow } from './ImageRow';
import { ImageTableProps } from './ImageTable.types';

export const ImageTable = ({ images, onImageUpdated }: ImageTableProps) => {
  const { t } = useTranslation('siriusWebApplication', { keyPrefix: 'image.list' });
  return (
    <Paper>
      <TableContainer>
        <Table>
          <colgroup>
            <col width="35%" />
            <col width="60%" />
            <col width="5%" />
          </colgroup>
          <TableHead>
            <TableRow>
              <TableCell variant="head">{t('label')}</TableCell>
              <TableCell variant="head">{t('id')}</TableCell>
              <TableCell variant="head" align="center">
                {t('delete')}
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody data-testid="images">
            {images.map((image) => (
              <ImageRow key={image.id} image={image} onImageUpdated={onImageUpdated} />
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Paper>
  );
};
