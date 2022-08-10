/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { PageListProps } from './PageList.types';

const usePageListStyles = makeStyles((theme) => ({
  pageList: {
    display: 'flex',
    flexDirection: 'column',
    borderRight: `1px solid ${theme.palette.divider}`,
    padding: `${theme.spacing(1)} ${theme.spacing(2)}`,
  },
}));

export const PageList = ({ pages }: PageListProps) => {
  const styles = usePageListStyles();
  return (
    <div className={styles.pageList}>
      {pages.map((page) => {
        return (
          <div key={page.id}>
            <Typography>{page.label}</Typography>
          </div>
        );
      })}
    </div>
  );
};
