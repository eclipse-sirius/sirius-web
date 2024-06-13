/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { HelpTooltip } from './HelpTooltip';
import { PropertySectionLabelProps } from './PropertySectionLabel.types';

const usePropertySectionLabelStyles = makeStyles((theme) => ({
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
  subscribers: {
    marginLeft: 'auto',
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    '& > *': {
      marginLeft: theme.spacing(0.5),
      marginRight: theme.spacing(0.5),
    },
  },
  avatar: {
    fontSize: '0.875rem',
    width: theme.spacing(2),
    height: theme.spacing(2),
  },
  typography: {
    lineHeight: '1.5',
  },
}));

export const PropertySectionLabel = ({ editingContextId, formId, widget }: PropertySectionLabelProps) => {
  const classes = usePropertySectionLabelStyles();

  if (!widget.label && !widget.hasHelpText) {
    return null;
  } else {
    return (
      <div className={classes.propertySectionLabel}>
        <Typography className={classes.typography} variant="subtitle2">
          {widget.label}
        </Typography>
        {widget.hasHelpText ? (
          <HelpTooltip editingContextId={editingContextId} formId={formId} widgetId={widget.id} />
        ) : null}
      </div>
    );
  }
};
