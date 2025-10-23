/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
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
