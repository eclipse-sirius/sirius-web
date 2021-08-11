/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import IconButton from '@material-ui/core/IconButton';
import Link from '@material-ui/core/Link';
import Snackbar from '@material-ui/core/Snackbar';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import { LinkPropertySectionProps } from 'properties/propertysections/LinkPropertySection.types';
import {
  LinkPropertySectionContext,
  LinkPropertySectionEvent,
  LinkPropertySectionMachine,
  SchemaValue,
} from 'properties/propertysections/LinkPropertySectionMachine';
import React from 'react';

/**
 * Defines the content of a Link property section.
 */
export const LinkPropertySection = ({ editingContextId, formId, widget, subscribers }: LinkPropertySectionProps) => {
  const [{ value: schemaValue, context }, dispatch] = useMachine<LinkPropertySectionContext, LinkPropertySectionEvent>(
    LinkPropertySectionMachine
  );
  const { toast } = schemaValue as SchemaValue;
  const { message } = context;

  return (
    <div>
      <Link id={widget.id} href={widget.url} rel="no-referrer" target="_blank">
        {widget.label}
      </Link>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' })}
        message={message}
        action={
          <IconButton size="small" aria-label="close" color="inherit" onClick={() => dispatch({ type: 'HIDE_TOAST' })}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </div>
  );
};
