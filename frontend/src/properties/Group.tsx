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
import { GroupProps } from 'properties/Group.types';
import React from 'react';
import { widgetToPropertySection } from './PropertySectionOperations';

const useGroupStyles = makeStyles((theme) => ({
  group: {
    display: 'flex',
    flexDirection: 'column',
  },
  title: {
    whiteSpace: 'nowrap',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
  },
  sections: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(2),
    },
  },
}));

export const Group = ({ editingContextId, formId, group, widgetSubscriptions, setSelection, readOnly }: GroupProps) => {
  const classes = useGroupStyles();
  let propertySections = group.widgets.map((widget) =>
    widgetToPropertySection(editingContextId, formId, widget, widgetSubscriptions, setSelection, readOnly)
  );

  return (
    <div className={classes.group}>
      <Typography variant="subtitle1" className={classes.title} gutterBottom>
        {group.label}
      </Typography>
      <div className={classes.sections}>{propertySections}</div>
    </div>
  );
};
